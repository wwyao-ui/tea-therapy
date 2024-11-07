import time
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager

# 设置 Chrome 浏览器选项
chrome_options = webdriver.ChromeOptions()
chrome_options.add_argument("--start-maximized")

# 启动 Chrome 浏览器
driver = webdriver.Chrome(service=Service(ChromeDriverManager().install()), options=chrome_options)

# 访问包含多个<a>链接的主页面
main_page_url = "https://baike.lingyaai.cn//hongcha.com"  # 替换为实际主页面URL
driver.get(main_page_url)
time.sleep(5)  # 等待页面加载

# 抓取主页面中所有的 href 链接
tea_urls = []
try:
    # 获取所有带有特定 class 的 <a> 标签
    link_elements = WebDriverWait(driver, 10).until(
        EC.presence_of_all_elements_located((By.XPATH, "//a[contains(@class, 'bk-item')]"))
    )
    # 提取每个 <a> 标签的 href 属性
    tea_urls = [link.get_attribute("href") for link in link_elements]
except Exception as e:
    print(f"Failed to retrieve links from main page: {e}")

# 初始化一个列表来存储每个茶叶的数据
all_tea_data = []

# 遍历每个链接并爬取数据
for url in tea_urls:
    driver.get(url)
    time.sleep(5)  # 等待页面加载

    # 初始化字典以存储数据
    tea_data = {"标题": "", "简介": "", "产地": "", "功效作用": "", "适宜人群": "", "禁忌人群": ""}

    # 抓取标题信息
    try:
        title_element = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//h1[@class='bkcon-tit active g-font18 g-font-b']"))
        )
        tea_data["标题"] = title_element.text
    except Exception as e:
        print(f"Failed to retrieve title for {url}: {e}")
        tea_data["标题"] = "标题不可用"

    # 抓取图片链接
    try:
        image_element = WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, "//div[@class='g-hide uni-flex uni-flex-cnt g-m5-b']//img"))
        )
        tea_data["图片链接"] = image_element.get_attribute("src")
    except Exception as e:
        print(f"Failed to retrieve image link for {url}: {e}")
        tea_data["图片链接"] = "图片链接不可用"

    # 定义每个字段的标题和对应内容的定位方式
    fields = {
        "产地": "产地",
        "功效作用": "功效作用",
        "适宜人群": "适宜人群",
        "禁忌人群": "禁忌人群"
    }

    # 循环查找每个字段的信息
    for key, field_text in fields.items():
        try:
            # 使用包含标题文字的 h3 标签找到信息块
            title_element = WebDriverWait(driver, 10).until(
                EC.presence_of_element_located(
                    (By.XPATH, f"//h3[contains(text(), '{field_text}')]/following-sibling::div"))
            )
            tea_data[key] = title_element.text
        except Exception as e:
            print(f"Failed to retrieve {key} for {url}: {e}")
            tea_data[key] = "信息不可用"

    # 将当前茶叶的数据添加到总列表中
    all_tea_data.append(tea_data)
    print(f"Retrieved data for {url}")

# 保存到 Excel
df = pd.DataFrame(all_tea_data)
df.to_excel('D:\\source\\batch_redtea.xlsx', index=False)
print("数据已保存到 batch_redtea.xlsx")

# 关闭浏览器
driver.quit()

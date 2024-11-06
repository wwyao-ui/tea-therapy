
from DrissionPage import ChromiumPage
import time
import random
import csv
page = ChromiumPage()

def sign_in():
    flag = True
    sign_in_page = ChromiumPage()
    while True:
        sign_in_page.get('https://www.xiaohongshu.com/explore')
        time.sleep(5)
        # < span class ="login-btn" data-v-c998debe="" > 登录 < / span >
        search_placeholder = sign_in_page.active_ele.html
        if '输入手机号' in search_placeholder:
            # 第一次运行需要扫码登录
            if flag:
                print("请扫码登录#")
                flag = False
            else:
                time.sleep(30)
        else:
            print("恭喜你，登陆成功！")
            break
        
def keyword_search(keyword):#关键词输入搜索函数
    sign_in()
    keyword_input = page.ele('.search-input')#关键词输入
    keyword_input.input(keyword)
    search_click = page.ele('.search-icon')
    search_click.click()

if __name__ == '__main__': 



    with open('关键词.txt','r',encoding='utf-8') as fff:
        fff = fff.readlines()
    #sign_in()
    for keword in fff:
        keword = keword.replace('\n','')
        keyword_search(keword)
        data = [
            ['标题', '作者昵称', '帖子链接', '帖子类型', '图片链接', '视频链接', '帖子文本', '点赞数', '收藏数',
             '评论数', '发布日期', '作者IP', '字作者ID', '作者简介', '作者关注数', '作者粉丝数', '作者点赞与收藏数',
             '评论者昵称', '评论内容', '评论日期', '评论IP']
        ]
        with open(keword + '.csv', 'a', newline='', encoding='utf-8') as file:
            writer = csv.writer(file)
            writer.writerows(data)
        '''
        data = [
            ['关键词','作者名字','作者账号id','分类','标题','时间','内容','评论数','收藏数','点赞数']
        ]
        with open(keword+'.csv', 'w', newline='',encoding='utf-8') as file:
            writer = csv.writer(file)
            writer.writerows(data)
        '''
        time.sleep(2)

 
        alist = []

        time.sleep(random.uniform(2,3))
        for j in range(15):
            alla = page.eles('.cover ld mask')
            for k in alla:
                if k.link not in alist:
                    alist.append(k.link)
            page.scroll.to_bottom()
            time.sleep(random.uniform(2,3))


        for j in alist:
            page.get(j,retry=99, show_errmsg=True, timeout=100)
            time.sleep(random.uniform(2,3))
            username = page.ele('.username').text #作者名字
            img = ''
            try:
                imgs = page.eles('.note-slider-img')
                for k in imgs:
                    img += (k.link + '\n')
                types = '图文'
            except:
                img = '-'
                types = '视频'
            videos = ''
            try:
                video = page.ele('tag:video').link
                types = '视频'
            except:
                video = '-'
                types = '图文'
            try:
                title = page.ele('.title').text
            except:
                title = '-'
            ns = page.ele('.desc').texts()
            note = ''
            for k in ns:
                note += k
            zan = page.ele('.like-wrapper like-active')
            if zan.ele('.count').text == '赞':
                zan_num = '0' #点赞数
            else:
                zan_num = zan.ele('.count').text
                            
            shoucang = page.ele('.collect-wrapper')
            if shoucang.ele('.count').text == '收藏':
                shoucang_num = '0' #收藏数
            else:
                shoucang_num = shoucang.ele('.count').text
                        
            pinglun = page.ele('.chat-wrapper')
            if pinglun.ele('.count').text == '评论':
                pinglun_num = '0' #评论数
            else:
                pinglun_num = pinglun.ele('.count').text
            date = page.ele('.date').text
            tab = page.new_tab()
            tab.get(page.ele('.info').ele('tag:a').link,retry=99, show_errmsg=True, timeout=100)
            time.sleep(random.uniform(2,3))
            try:
                ip = tab.ele('.user-IP').text #作者IP地址
            except:
                ip = '-'
            ids = tab.ele('.user-redId').text #作者小红书id
            try:
                authordesc = tab.ele('.user-desc').text #作者简介
            except:
                authordesc = '-'
            authorguan = tab.ele('.user-interactions').eles('.count')[0].text #作者关注数
            authorfensi = tab.ele('.user-interactions').eles('.count')[1].text #作者粉丝
            authorzan = tab.ele('.user-interactions').eles('.count')[2].text #作者点赞
            tab.close()
            time.sleep(random.uniform(1,2))
            try:
                page.ele('.list-container').hover()
                time.sleep(2)
                for _ in range(10):  # 滚动20次
                    #page.ele('.list-container').hover()
                    time.sleep(random.uniform(1,2))
                    ls = page.eles('.parent-comment')
                    for i in range(len(ls)):
                        try:
                            ls[i].hover()
                            page.scroll(0, 100)
                            time.sleep(random.uniform(1,2))
                            if 1:
                                break
                        except:
                            pass

                ls = page.eles('.parent-comment')

                for k in ls:
                    commentname = k.ele('.name').text #评论名字
                    try:
                        commentscontent = k.ele('.content').text #评论内容
                    except:
                        commentscontent = '-'
                    datesss = k.ele('.info').eles('tag:span')
                    try:
                        commentdate = datesss[0].text #评论时间
                    except:
                        commentdate = '-'
                    try:
                        commentip = datesss[1].text #评论ip
                    except:
                        commentip = '-'
                    print(title,username,j,types,img,video,note,zan_num,shoucang_num,pinglun_num,date,ip,ids,authordesc,authorguan,authorfensi,authorzan,commentname,commentscontent,commentdate,commentip)
                    data = [
                        [title,username,j,types,img,video,note,zan_num,shoucang_num,pinglun_num,date,ip,ids,authordesc,authorguan,authorfensi,authorzan,commentname,commentscontent,commentdate,commentip]
                    ]
                    with open(keword+'.csv', 'a', newline='',encoding='utf-8') as file:
                        writer = csv.writer(file)
                        writer.writerows(data)
            except:
                commentname = '-'
                commentscontent = '-'
                commentdate = '-'
                commentip = '-'
                print(title,username,j,types,img,video,note,zan_num,shoucang_num,pinglun_num,date,ip,ids,authordesc,authorguan,authorfensi,authorzan,commentname,commentscontent,commentdate,commentip)
                data = [
                    [title,username,j,types,img,video,note,zan_num,shoucang_num,pinglun_num,date,ip,ids,authordesc,authorguan,authorfensi,authorzan,commentname,commentscontent,commentdate,commentip]
                ]
                with open(keword+'.csv', 'a', newline='',encoding='utf-8') as file:
                    writer = csv.writer(file)
                    writer.writerows(data)
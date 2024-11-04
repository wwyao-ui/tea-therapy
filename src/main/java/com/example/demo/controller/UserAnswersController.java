package com.example.demo.controller;


import com.example.demo.mapper.OptionsMapper;
import com.example.demo.mapper.UserAnswersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author he
 * @since 2024-07-09
 */
@RestController
@RequestMapping("/user-answers")
public class UserAnswersController {

    @Autowired
    UserAnswersMapper userAnswersMapper;
    @Autowired
    OptionsMapper optionsMapper;

    /*
    @RequestMapping("score")
    @ResponseBody
    public ResultUtil score(String userId) {
        UserAnswers userAnswers = userAnswersMapper.selectUserAnswersById(userId);
        if (userAnswers == null) {
            return ResultUtil.error("用户答案未找到");
        }

        // 问卷ID列表
        List<String> questionnaireIds = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9");
        // 问题ID列表
        List<String> questionIds = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        // 特殊选项ID列表
        List<String> specialIds = Arrays.asList("2", "3", "4", "5", "7", "8");
        int totalScore = 0;

        //遍历问卷列表
        for  (String userQuestionnaireId : questionnaireIds) {
            // 遍历问题ID列表
            for (String questionId : questionIds) {
                System.out.println("当前问题ID: " + questionId);

                // 假设这里我们需要根据questionId来获取选项
                Options options = optionsMapper.selectOptionById(questionId);

                if (options != null) {
                    try {
                        int score;
                        // 检查是否满足特定条件：问卷ID为"1"且问题ID在特殊选项ID列表中
                        if ("1".equals(userQuestionnaireId) && specialIds.contains(questionId)) {
                            score = transform(Integer.parseInt(options.getId())); // 假设transform是分数转换方法
                        } else {
                            score = Integer.parseInt(options.getId()); // 假设选项的ID代表分数
                        }
                        totalScore += score;
                    } catch (NumberFormatException e) {
                        System.err.println("选项ID不是有效的整数: " + options.getId());
                    }
                }
            }
        }

        System.out.println("总分数: " + totalScore);
        return ResultUtil.ok(totalScore);
    }

     */

}

package com.example.demo.common;

import java.util.HashMap;
import java.util.Map;

public class TCMBodyConstitution {
    private static final int THRESHOLD_PINGHE = 60; // 平和质的阈值
    private static final int THRESHOLD_BIOPHASIC = 40; // 偏颇体质的阈值
    private static final int THRESHOLD_TENDENCY = 30; // 倾向于偏颇体质的阈值

    private Map<String, Integer> constitutionScores = new HashMap<>();

    public TCMBodyConstitution() {
        constitutionScores.put("平和质", 0); // 平和质
        constitutionScores.put("气虚质", 0); // 气虚质
        constitutionScores.put("阳虚质", 0); // 阳虚质
        constitutionScores.put("阴虚质", 0); // 阴虚质
        constitutionScores.put("痰湿质", 0); // 痰湿质
        constitutionScores.put("湿热质", 0); // 湿热质
        constitutionScores.put("血瘀质", 0); // 血瘀质
        constitutionScores.put("气郁质", 0); // 气郁质
        constitutionScores.put("特禀质", 0); // 特禀质
    }



    public void setScore(String constitution, int score) {
        constitutionScores.put(constitution, score);
    }

    public String determineConstitution() {
        int pingHeScore = constitutionScores.get("平和质");

        if (pingHeScore >= THRESHOLD_PINGHE) {
            boolean isPingHe = true;
            for (String key : constitutionScores.keySet()) {
                if (!key.equals("平和质") && constitutionScores.get(key) >= THRESHOLD_BIOPHASIC) {
                    isPingHe = false;
                    break;
                }
            }
            if (isPingHe) {
                return "平和质"; // 平和质
            }
        }

        for (String key : constitutionScores.keySet()) {
            if (!key.equals("平和质")) {
                if (constitutionScores != null) {
                    int score = constitutionScores.get(key);
                    if (score >= THRESHOLD_BIOPHASIC) {
                        return key; // 直接是偏颇体质
                    }
                }
            }
        }

        return "Unknown"; // 未知或需要进一步检查
    }
}

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Order {
    static final String headerMale = ("Здравствуйте, дорогой ");
    static final String headerFemale = ("Здравствуйте, дорогая ");
    static final String footer = ("С уважением, автоматизированная система мониторинга.");

    static HashSet<String> unicalKeys(HashMap<String, String> one, HashMap<String, String> two) {
        Set<String> o = one.keySet();
        Set<String> t = two.keySet();
        HashSet<String> unical = new HashSet<>(o);
        unical.removeAll(t);
        return unical;
    }

    static StringBuilder changedURL(boolean isMale, String name,
                                    HashMap<String, String> today, HashMap<String, String> yesterday) {
        StringBuilder result = new StringBuilder();
        if (isMale) result.append(headerMale);
        else result.append(headerFemale);
        result.append(name).append("\n\n");

        result.append("За последние сутки во вверенных Вам сайтах произошли следующие изменения:\n\n");

        result.append("Исчезли следующие страницы:\n");
        for (String item : unicalKeys(yesterday, today)) {
            result.append(item).append("\n");
        }

        result.append("Появились следующие страницы:\n");
        for (String item : unicalKeys(today, yesterday)) {
            result.append(item).append("\n");
        }

        result.append("Изменились следующие страницы:\n");
        today = new HashMap<>(today);
        today.keySet().removeAll(unicalKeys(today, yesterday));
        yesterday = new HashMap<>(yesterday);
        yesterday.keySet().removeAll(unicalKeys(yesterday, today));
        for (HashMap.Entry<String, String> entry : today.entrySet()) {
            String value1 = entry.getValue();
            String value2 = yesterday.get(entry.getKey());
            if (value2 != null && !value1.equals(value2)) {
                result.append(entry.getKey()).append("\n");
            }
        }

        result.append("\n").append(footer);
        return result;
    }

    public static void main(String[] args) {
        HashMap<String, String> yesterday = new HashMap<>();
        yesterday.put("www.radio.ru", "All was good");
        yesterday.put("www.facebook.com", "Registration");

        HashMap<String, String> today = new HashMap<>();
        today.put("www.radio.ru", "All will be good");
        today.put("www.vk.com", "Registration");

        System.out.println(changedURL(false, "Ольга Викторовна", today, yesterday));
    }
}

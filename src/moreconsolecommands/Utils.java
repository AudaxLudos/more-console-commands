package moreconsolecommands;

import com.fs.starfarer.api.Global;
import lunalib.lunaSettings.LunaSettings;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    public static final String MCC_SETTINGS_FILE = "data/config/mcc_settings.ini";
    public static final String lunaLibId = "lunalib";
    public static final String modId = "moreconsolecommands";

    public static boolean getBoolean(JSONObject data, String id) throws JSONException {
        if (Global.getSettings().getModManager().isModEnabled(lunaLibId)) {
            Boolean result = LunaSettings.getBoolean(modId, id);
            if (result == null) {
                return false;
            }
            return result;
        }
        return data.getBoolean(id);
    }
}

package kr.dogfoot.hwplib.object.bodytext.control.form.properties;

import kr.dogfoot.hwplib.object.etc.HWPString;

import java.util.HashMap;

public class PropertySet extends Property {
    private HashMap<String, Property> propertyMap;

    public PropertySet(String name) {
        setName(name);
        propertyMap = new HashMap<>();
    }

    public PropertyNormal addNewNormalProperty(String name, String type) {
        PropertyNormal propertyNormal = new PropertyNormal(name);
        propertyNormal.setType(PropertyType.fromString(type));
        propertyMap.put(name, propertyNormal);
        return propertyNormal;
    }

    public PropertySet addNewPropertySet(String name) {
        PropertySet propertySet = new PropertySet(name);
        propertyMap.put(name, propertySet);
        return propertySet;
    }

    public Property getProperty(String name) {
        return propertyMap.get(name);
    }

    @Override
    public PropertyType getType() {
        return PropertyType.Set;
    }

    public void parse(String data) {
        while(data.length() > 0) {
            int position = data.indexOf(":");
            String name = data.substring(0, position);
            data = data.substring(position + 1);

            position = data.indexOf(":");
            String type = data.substring(0, position);
            data = data.substring(position + 1);

            if (type.equals("set")) {
                position = data.indexOf(":");
                int length = Integer.parseInt(data.substring(0, position));
                data = data.substring(position + 1);
                String setData = data.substring(0, length);
                data = data.substring(length + 1);

                addNewPropertySet(name).parse(setData);
            } else if(type.equals("wstring")) {
                position = data.indexOf(":");
                int length = Integer.parseInt(data.substring(0, position));
                data = data.substring(position + 1);

                String value = data.substring(0, length);
                data = data.substring(length + 1);

                addNewNormalProperty(name, type).setValue(value);
          } else {
                position = data.indexOf(" ");
                String value = data.substring(0, position);
                data = data.substring(position + 1);

                addNewNormalProperty(name, type).setValue(value);
            }
        }
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (getName() != null && getName().length() > 0) {
            StringBuffer sb2 = new StringBuffer();
            for (Property property : propertyMap.values()) {
                sb2.append(property.toString()).append(" ");
            }

            sb
                    .append(getName())
                    .append(":")
                    .append(getType())
                    .append(":")
                    .append(sb2.length())
                    .append(":")
                    .append(sb2);
        } else {
            for (Property property : propertyMap.values()) {
                sb.append(property.toString()).append(" ");
            }
        }
        return sb.toString();
    }

    public HWPString toHWPString() {
        HWPString ret = new HWPString();
        ret.fromUTF16LEString(toString());
        return ret;
    }
}

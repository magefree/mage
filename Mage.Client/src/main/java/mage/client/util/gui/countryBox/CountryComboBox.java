/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.client.util.gui.countryBox;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * A custom combo box with its own renderer and editor.
 * @author wwww.codejava.net
 *
 */
public class CountryComboBox extends JComboBox {
    
    private final DefaultComboBoxModel model;

    public static String[][] countryList = {
        {"Afghanistan", "af"},
        {"Åland Islands", "ax"},
        {"Albania", "al"},
        {"Algeria", "dz"},
        {"American Samoa", "as"},
        {"Andorra", "ad"},
        {"Angola", "an"},
        {"Anguilla", "ai"},
        {"Antarctica", "ao"},
        {"Antigua and Barbuda", "ag"},
        {"Argentina", "ar"},
        {"Armenia", "am"},
        {"Aruba", "aw"},
        {"Australia", "au"},
        {"Austria", "at"},
        {"Azerbaijan", "az"},
        {"Bahamas", "bs"},
        {"Bahrain", "bh"},
        {"Bangladesh", "bd"},
        {"Barbados", "bb"},
        {"Belarus", "by"},
        {"Belgium", "be"},
        {"Belize", "bz"},
        {"Benin", "bj"},
        {"Bermuda", "bm"},
        {"Bhutan", "bt"},
        {"Bolivia, Plurinational State of", "bo"},
        {"Bosnia and Herzegovina", "ba"},
        {"Botswana", "bw"},
        {"Bouvet Island", "bv"},
        {"Brazil", "br"},
        {"British Indian Ocean Territory", "io"},
        {"Brunei Darussalam", "bn"},
        {"Bulgaria", "bg"},
        {"Burkina Faso", "bf"},
        {"Burundi", "bi"},
        {"Cabo Verde", "cv"},
        {"Cambodia", "kh"},
        {"Cameroon", "cm"},
        {"Canada", "ca"},
        {"Catalonia", "catalonia"},
        {"Cayman Islands", "ky"},
        {"Central African Republic", "cf"},
        {"Chad", "td"},
        {"Chile", "cl"},
        {"China", "cn"},
        {"Christmas Island", "cx"},
        {"Cocos (Keeling) Islands", "cc"},
        {"Colombia", "co"},
        {"Comoros", "km"},
        {"Congo", "cg"},
        {"Congo, the Democratic Republic of the", "cd"},
        {"Cook Islands", "ck"},
        {"Costa Rica", "cr"},
        {"Côte d'Ivoire", "ci"},
        {"Croatia", "hr"},
        {"Cuba", "cu"},
        {"Cyprus", "cy"},
        {"Czech Republic", "cz"},
        {"Denmark", "dk"},
        {"Djibouti", "dj"},
        {"Dominica", "dm"},
        {"Dominican Republic", "do"},
        {"Ecuador", "ec"},
        {"Egypt", "eg"},
        {"El Salvador", "sv"},
        {"England", "england"},
        {"Equatorial Guinea", "gq"},
        {"Eritrea", "er"},
        {"Estonia", "ee"},
        {"Ethiopia", "et"},
        {"European Union", "europeanunion"},
        {"Falkland Islands (Malvinas)", "fk"},
        {"Faroe Islands", "fo"},
        {"Fiji", "fj"},
        {"Finland", "fi"},
        {"France", "fr"},
        {"French Guiana", "gf"},
        {"French Polynesia", "pf"},
        {"French Southern Territories", "tf"},
        {"Gabon", "ga"},
        {"Gambia", "gm"},
        {"Georgia", "ge"},
        {"Germany", "de"},
        {"Ghana", "gh"},
        {"Gibraltar", "gi"},
        {"Greece", "gr"},
        {"Greenland", "gl"},
        {"Grenada", "gd"},
        {"Guadeloupe", "gp"},
        {"Guam", "gu"},
        {"Guatemala", "gt"},
        {"Guinea", "gn"},
        {"Guinea-Bissau", "gw"},
        {"Guyana", "gy"},
        {"Haiti", "ht"},
        {"Heard Island and McDonald Islands", "hm"},
        {"Holy See", "va"},
        {"Honduras", "hn"},
        {"Hong Kong", "hk"},
        {"Hungary", "hu"},
        {"Iceland", "is"},
        {"India", "in"},
        {"Indonesia", "id"},
        {"Iran, Islamic Republic of", "ir"},
        {"Iraq", "iq"},
        {"Ireland", "ie"},
        {"Israel", "il"},
        {"Italy", "it"},
        {"Jamaica", "jm"},
        {"Japan", "jp"},
        {"Jordan", "jo"},
        {"Kazakhstan", "kz"},
        {"Kenya", "ke"},
        {"Kiribati", "ki"},
        {"Korea, Democratic People's Republic of", "kp"},
        {"Korea, Republic of", "kr"},
        {"Kuwait", "kw"},
        {"Kyrgyzstan", "kg"},
        {"Lao People's Democratic Republic", "la"},
        {"Latvia", "lv"},
        {"Lebanon", "lb"},
        {"Lesotho", "ls"},
        {"Liberia", "lr"},
        {"Libya", "ly"},
        {"Liechtenstein", "li"},
        {"Lithuania", "lt"},
        {"Luxembourg", "lu"},
        {"Macao", "mo"},
        {"Macedonia, the former Yugoslav Republic of", "mk"},
        {"Madagascar", "mg"},
        {"Malawi", "mw"},
        {"Malaysia", "my"},
        {"Maldives", "mv"},
        {"Mali", "ml"},
        {"Malta", "mt"},
        {"Marshall Islands", "mh"},
        {"Martinique", "mq"},
        {"Mauritania", "mr"},
        {"Mauritius", "mu"},
        {"Mayotte", "yt"},
        {"Mexico", "mx"},
        {"Micronesia, Federated States of", "fm"},
        {"Moldova, Republic of", "md"},
        {"Monaco", "mc"},
        {"Mongolia", "mn"},
        {"Montenegro", "me"},
        {"Montserrat", "ms"},
        {"Morocco", "ma"},
        {"Mozambique", "mz"},
        {"Myanmar", "mm"},
        {"Namibia", "na"},
        {"Nauru", "nr"},
        {"Nepal", "np"},
        {"Netherlands", "nl"},
        {"New Caledonia", "nc"},
        {"New Zealand", "nz"},
        {"Nicaragua", "ni"},
        {"Niger", "ne"},
        {"Nigeria", "ng"},
        {"Niue", "nu"},
        {"Norfolk Island", "nf"},
        {"Northern Mariana Islands", "mp"},
        {"Norway", "no"},
        {"Oman", "om"},
        {"Pakistan", "pk"},
        {"Palau", "pw"},
        {"Palestine, State of", "ps"},
        {"Panama", "pa"},
        {"Papua New Guinea", "pg"},
        {"Paraguay", "py"},
        {"Peru", "pe"},
        {"Philippines", "ph"},
        {"Pitcairn", "pn"},
        {"Poland", "pl"},
        {"Portugal", "pt"},
        {"Puerto Rico", "pr"},
        {"Qatar", "qa"},
        {"Réunion", "re"},
        {"Romania", "ro"},
        {"Russian Federation", "ru"},
        {"Rwanda", "rw"},
        {"Saint Helena, Ascension and Tristan da Cunha", "sh"},
        {"Saint Kitts and Nevis", "kn"},
        {"Saint Lucia", "lc"},
        {"Saint Pierre and Miquelon", "pm"},
        {"Saint Vincent and the Grenadines", "vc"},
        {"Samoa", "ws"},
        {"San Marino", "sm"},
        {"Sao Tome and Principe", "st"},
        {"Saudi Arabia", "sa"},
        {"Scotland", "scotland"},
        {"Senegal", "sn"},
        {"Serbia", "rs"},
        {"Seychelles", "sc"},
        {"Sierra Leone", "sl"},
        {"Singapore", "sg"},
        {"Slovakia", "sk"},
        {"Slovenia", "si"},
        {"Solomon Islands", "sb"},
        {"Somalia", "so"},
        {"South Africa", "za"},
        {"South Georgia and the South Sandwich Islands", "gs"},
        {"Spain", "es"},
        {"Sri Lanka", "lk"},
        {"Sudan", "sd"},
        {"Suriname", "sr"},
        {"Svalbard and Jan Mayen", "sj"},
        {"Swaziland", "sz"},
        {"Sweden", "se"},
        {"Switzerland", "ch"},
        {"Syrian Arab Republic", "sy"},
        {"Taiwan, Province of China", "tw"},
        {"Tajikistan", "tj"},
        {"Tanzania, United Republic of", "tz"},
        {"Thailand", "th"},
        {"Timor-Leste", "tl"},
        {"Togo", "tg"},
        {"Tokelau", "tk"},
        {"Tonga", "to"},
        {"Trinidad and Tobago", "tt"},
        {"Tunisia", "tn"},
        {"Turkey", "tr"},
        {"Turkmenistan", "tm"},
        {"Turks and Caicos Islands", "tc"},
        {"Tuvalu", "tv"},
        {"Uganda", "ug"},
        {"Ukraine", "ua"},
        {"United Arab Emirates", "ae"},
        {"United Kingdom of Great Britain and Northern Ireland", "gb"},
        {"United States Minor Outlying Islands", "um"},
        {"United States of America", "us"},
        {"Uruguay", "uy"},
        {"Uzbekistan", "uz"},
        {"Vanuatu", "vu"},
        {"Venezuela, Bolivarian Republic of", "ve"},
        {"Viet Nam", "vn"},
        {"Virgin Islands, British", "vg"},
        {"Virgin Islands, U.S.", "vi"},
        {"Wales", "wales"},
        {"Wallis and Futuna", "wf"},
        {"World", "world"},
        {"Western Sahara", "eh"},
        {"Yemen", "ye"},
        {"Zambia", "zm"},
        {"Zimbabwe", "zw"},
    };    
    
    @SuppressWarnings("unchecked")
    public CountryComboBox() {
        model = new DefaultComboBoxModel();
        setModel(model);
        setRenderer(new CountryItemRenderer());
        setEditor(new CountryItemEditor());
        addItems(countryList);
    }
     
    /**
     * Add an array items to this combo box.
     * Each item is an array of two String elements:
     * - first element is country name.
     * - second element is path of an image file for country flag.
     * @param items
     */
    @SuppressWarnings("unchecked")
    public void addItems(String[][] items) {
        for (String[] anItem : items) {
            model.addElement(anItem);
        }
    }

    @Override
    public Object getSelectedItem() {
        return super.getSelectedItem(); //To change body of generated methods, choose Tools | Templates.
    }
    

}

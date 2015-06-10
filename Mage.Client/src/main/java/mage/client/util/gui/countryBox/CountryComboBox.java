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
        {"Afghanistan", "af.png"},
        {"Åland Islands", "ax.png"},
        {"Albania", "al.png"},
        {"Algeria", "dz.png"},
        {"American Samoa", "as.png"},
        {"Andorra", "ad.png"},
        {"Angola", "an.png"},
        {"Anguilla", "ai.png"},
        {"Antarctica", "ao.png"},
        {"Antigua and Barbuda", "ag.png"},
        {"Argentina", "ar.png"},
        {"Armenia", "am.png"},
        {"Aruba", "aw.png"},
        {"Australia", "au.png"},
        {"Austria", "at.png"},
        {"Azerbaijan", "az.png"},
        {"Bahamas", "bs.png"},
        {"Bahrain", "bh.png"},
        {"Bangladesh", "bd.png"},
        {"Barbados", "bb.png"},
        {"Belarus", "by.png"},
        {"Belgium", "be.png"},
        {"Belize", "bz.png"},
        {"Benin", "bj.png"},
        {"Bermuda", "bm.png"},
        {"Bhutan", "bt.png"},
        {"Bolivia, Plurinational State of", "bo.png"},
        {"Bosnia and Herzegovina", "ba.png"},
        {"Botswana", "bw.png"},
        {"Bouvet Island", "bv.png"},
        {"Brazil", "br.png"},
        {"British Indian Ocean Territory", "io.png"},
        {"Brunei Darussalam", "bn.png"},
        {"Bulgaria", "bg.png"},
        {"Burkina Faso", "bf.png"},
        {"Burundi", "bi.png"},
        {"Cabo Verde", "cv.png"},
        {"Cambodia", "kh.png"},
        {"Cameroon", "cm.png"},
        {"Canada", "ca.png"},
        {"Catalonia", "catalonia.png"},
        {"Cayman Islands", "ky.png"},
        {"Central African Republic", "cf.png"},
        {"Chad", "td.png"},
        {"Chile", "cl.png"},
        {"China", "cn.png"},
        {"Christmas Island", "cx.png"},
        {"Cocos (Keeling) Islands", "cc.png"},
        {"Colombia", "co.png"},
        {"Comoros", "km.png"},
        {"Congo", "cg.png"},
        {"Congo, the Democratic Republic of the", "cd.png"},
        {"Cook Islands", "ck.png"},
        {"Costa Rica", "cr.png"},
        {"Côte d'Ivoire", "ci.png"},
        {"Croatia", "hr.png"},
        {"Cuba", "cu.png"},
        {"Cyprus", "cy.png"},
        {"Czech Republic", "cz.png"},
        {"Denmark", "dk.png"},
        {"Djibouti", "dj.png"},
        {"Dominica", "dm.png"},
        {"Dominican Republic", "do.png"},
        {"Ecuador", "ec.png"},
        {"Egypt", "eg.png"},
        {"El Salvador", "sv.png"},
        {"England", "england.png"},
        {"Equatorial Guinea", "gq.png"},
        {"Eritrea", "er.png"},
        {"Estonia", "ee.png"},
        {"Ethiopia", "et.png"},
        {"European Union", "europeanunion.png"},
        {"Falkland Islands (Malvinas)", "fk.png"},
        {"Faroe Islands", "fo.png"},
        {"Fiji", "fj.png"},
        {"Finland", "fi.png"},
        {"France", "fr.png"},
        {"French Guiana", "gf.png"},
        {"French Polynesia", "pf.png"},
        {"French Southern Territories", "tf.png"},
        {"Gabon", "ga.png"},
        {"Gambia", "gm.png"},
        {"Georgia", "ge.png"},
        {"Germany", "de.png"},
        {"Ghana", "gh.png"},
        {"Gibraltar", "gi.png"},
        {"Greece", "gr.png"},
        {"Greenland", "gl.png"},
        {"Grenada", "gd.png"},
        {"Guadeloupe", "gp.png"},
        {"Guam", "gu.png"},
        {"Guatemala", "gt.png"},
        {"Guinea", "gn.png"},
        {"Guinea-Bissau", "gw.png"},
        {"Guyana", "gy.png"},
        {"Haiti", "ht.png"},
        {"Heard Island and McDonald Islands", "hm.png"},
        {"Holy See", "va.png"},
        {"Honduras", "hn.png"},
        {"Hong Kong", "hk.png"},
        {"Hungary", "hu.png"},
        {"Iceland", "is.png"},
        {"India", "in.png"},
        {"Indonesia", "id.png"},
        {"Iran, Islamic Republic of", "ir.png"},
        {"Iraq", "iq.png"},
        {"Ireland", "ie.png"},
        {"Israel", "il.png"},
        {"Italy", "it.png"},
        {"Jamaica", "jm.png"},
        {"Japan", "jp.png"},
        {"Jordan", "jo.png"},
        {"Kazakhstan", "kz.png"},
        {"Kenya", "ke.png"},
        {"Kiribati", "ki.png"},
        {"Korea, Democratic People's Republic of", "kp.png"},
        {"Korea, Republic of", "kr.png"},
        {"Kuwait", "kw.png"},
        {"Kyrgyzstan", "kg.png"},
        {"Lao People's Democratic Republic", "la.png"},
        {"Latvia", "lv.png"},
        {"Lebanon", "lb.png"},
        {"Lesotho", "ls.png"},
        {"Liberia", "lr.png"},
        {"Libya", "ly.png"},
        {"Liechtenstein", "li.png"},
        {"Lithuania", "lt.png"},
        {"Luxembourg", "lu.png"},
        {"Macao", "mo.png"},
        {"Macedonia, the former Yugoslav Republic of", "mk.png"},
        {"Madagascar", "mg.png"},
        {"Malawi", "mw.png"},
        {"Malaysia", "my.png"},
        {"Maldives", "mv.png"},
        {"Mali", "ml.png"},
        {"Malta", "mt.png"},
        {"Marshall Islands", "mh.png"},
        {"Martinique", "mq.png"},
        {"Mauritania", "mr.png"},
        {"Mauritius", "mu.png"},
        {"Mayotte", "yt.png"},
        {"Mexico", "mx.png"},
        {"Micronesia, Federated States of", "fm.png"},
        {"Moldova, Republic of", "md.png"},
        {"Monaco", "mc.png"},
        {"Mongolia", "mn.png"},
        {"Montenegro", "me.png"},
        {"Montserrat", "ms.png"},
        {"Morocco", "ma.png"},
        {"Mozambique", "mz.png"},
        {"Myanmar", "mm.png"},
        {"Namibia", "na.png"},
        {"Nauru", "nr.png"},
        {"Nepal", "np.png"},
        {"Netherlands", "nl.png"},
        {"New Caledonia", "nc.png"},
        {"New Zealand", "nz.png"},
        {"Nicaragua", "ni.png"},
        {"Niger", "ne.png"},
        {"Nigeria", "ng.png"},
        {"Niue", "nu.png"},
        {"Norfolk Island", "nf.png"},
        {"Northern Mariana Islands", "mp.png"},
        {"Norway", "no.png"},
        {"Oman", "om.png"},
        {"Pakistan", "pk.png"},
        {"Palau", "pw.png"},
        {"Palestine, State of", "ps.png"},
        {"Panama", "pa.png"},
        {"Papua New Guinea", "pg.png"},
        {"Paraguay", "py.png"},
        {"Peru", "pe.png"},
        {"Philippines", "ph.png"},
        {"Pitcairn", "pn.png"},
        {"Poland", "pl.png"},
        {"Portugal", "pt.png"},
        {"Puerto Rico", "pr.png"},
        {"Qatar", "qa.png"},
        {"Réunion", "re.png"},
        {"Romania", "ro.png"},
        {"Russian Federation", "ru.png"},
        {"Rwanda", "rw.png"},
        {"Saint Helena, Ascension and Tristan da Cunha", "sh.png"},
        {"Saint Kitts and Nevis", "kn.png"},
        {"Saint Lucia", "lc.png"},
        {"Saint Pierre and Miquelon", "pm.png"},
        {"Saint Vincent and the Grenadines", "vc.png"},
        {"Samoa", "ws.png"},
        {"San Marino", "sm.png"},
        {"Sao Tome and Principe", "st.png"},
        {"Saudi Arabia", "sa.png"},
        {"Scotland", "scotland.png"},
        {"Senegal", "sn.png"},
        {"Serbia", "rs.png"},
        {"Seychelles", "sc.png"},
        {"Sierra Leone", "sl.png"},
        {"Singapore", "sg.png"},
        {"Slovakia", "sk.png"},
        {"Slovenia", "si.png"},
        {"Solomon Islands", "sb.png"},
        {"Somalia", "so.png"},
        {"South Africa", "za.png"},
        {"South Georgia and the South Sandwich Islands", "gs.png"},
        {"Spain", "es.png"},
        {"Sri Lanka", "lk.png"},
        {"Sudan", "sd.png"},
        {"Suriname", "sr.png"},
        {"Svalbard and Jan Mayen", "sj.png"},
        {"Swaziland", "sz.png"},
        {"Sweden", "se.png"},
        {"Switzerland", "ch.png"},
        {"Syrian Arab Republic", "sy.png"},
        {"Taiwan, Province of China", "tw.png"},
        {"Tajikistan", "tj.png"},
        {"Tanzania, United Republic of", "tz.png"},
        {"Thailand", "th.png"},
        {"Timor-Leste", "tl.png"},
        {"Togo", "tg.png"},
        {"Tokelau", "tk.png"},
        {"Tonga", "to.png"},
        {"Trinidad and Tobago", "tt.png"},
        {"Tunisia", "tn.png"},
        {"Turkey", "tr.png"},
        {"Turkmenistan", "tm.png"},
        {"Turks and Caicos Islands", "tc.png"},
        {"Tuvalu", "tv.png"},
        {"Uganda", "ug.png"},
        {"Ukraine", "ua.png"},
        {"United Arab Emirates", "ae.png"},
        {"United Kingdom of Great Britain and Northern Ireland", "gb.png"},
        {"United States Minor Outlying Islands", "um.png"},
        {"United States of America", "us.png"},
        {"Uruguay", "uy.png"},
        {"Uzbekistan", "uz.png"},
        {"Vanuatu", "vu.png"},
        {"Venezuela, Bolivarian Republic of", "ve.png"},
        {"Viet Nam", "vn.png"},
        {"Virgin Islands, British", "vg.png"},
        {"Virgin Islands, U.S.", "vi.png"},
        {"Wales", "wales.png"},
        {"Wallis and Futuna", "wf.png"},
        {"World", "world.png"},
        {"Western Sahara", "eh.png"},
        {"Yemen", "ye.png"},
        {"Zambia", "zm.png"},
        {"Zimbabwe", "zw.png"},
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
    

}

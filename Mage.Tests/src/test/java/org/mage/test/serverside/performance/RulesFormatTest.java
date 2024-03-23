package org.mage.test.serverside.performance;

import mage.MageObject;
import mage.cards.repository.CardScanner;
import mage.util.CardUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JayDi85
 */
public class RulesFormatTest {

    @Test
    @Ignore // debug only, can be slow (10+ secs)
    public void test_InfiniteFreezeOnStringReplace_Fast() {
        // use case: in some point of time java's string replace code from GainAbilityAttachedEffect can freeze forever
        // details: https://github.com/magefree/mage/issues/11285#issuecomment-2011326865
        // status: can't reproduce original bug, maybe regexp freeze related to JRE versions/builds

        CardScanner.scan();
        CardScanner.getAllCards().forEach(card -> {
            List<String> possibleObjectNames = new ArrayList<>();
            // any card names
            CardUtil.getObjectPartsAsObjects(card)
                    .stream()
                    .map(MageObject::getName)
                    .forEach(possibleObjectNames::add);
            // all names from GainAbilityAttachedEffect
            possibleObjectNames.add("creature");
            possibleObjectNames.add("permanent");
            possibleObjectNames.add("land");
            possibleObjectNames.add("planeswalker");

            card.getAbilities().forEach(ability -> {
                possibleObjectNames.forEach(name -> {
                    // simulate replacement code from GainAbilityAttachedEffect::getText
                    String sourceName = "This " + name;
                    ability.getRule(sourceName);
                });
            });
        });
    }
}

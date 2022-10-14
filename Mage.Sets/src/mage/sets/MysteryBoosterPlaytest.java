package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * https://mtg.fandom.com/wiki/Mystery_Booster/Test_cards
 * https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
 * https://scryfall.com/sets/cmb1
 *
 */
public class MysteryBoosterPlaytest extends ExpansionSet {

    private static final MysteryBoosterPlaytest instance = new MysteryBoosterPlaytest();

    public static MysteryBoosterPlaytest getInstance() {
        return instance;
    }

    private MysteryBoosterPlaytest() {
        super("Mystery Booster Playtest", "CMB1", ExpansionSet.buildDate(2019, 11, 7), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Slivdrazi Monstrosity", 102, Rarity.RARE, mage.cards.s.SlivdraziMonstrosity.class));
    }

}

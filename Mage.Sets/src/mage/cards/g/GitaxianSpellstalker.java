package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GitaxianSpellstalker extends CardImpl {

    public GitaxianSpellstalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.JACKAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.color.setRed(true);
        this.nightCard = true;

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Prowess
        this.addAbility(new ProwessAbility());

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private GitaxianSpellstalker(final GitaxianSpellstalker card) {
        super(card);
    }

    @Override
    public GitaxianSpellstalker copy() {
        return new GitaxianSpellstalker(this);
    }
}

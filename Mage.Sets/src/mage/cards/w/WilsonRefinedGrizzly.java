package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WilsonRefinedGrizzly extends CardImpl {

    public WilsonRefinedGrizzly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), true));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private WilsonRefinedGrizzly(final WilsonRefinedGrizzly card) {
        super(card);
    }

    @Override
    public WilsonRefinedGrizzly copy() {
        return new WilsonRefinedGrizzly(this);
    }
}

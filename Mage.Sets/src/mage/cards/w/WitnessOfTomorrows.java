package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WitnessOfTomorrows extends CardImpl {

    public WitnessOfTomorrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(new ScryEffect(1, false), new ManaCostsImpl("{3}{U}")));
    }

    private WitnessOfTomorrows(final WitnessOfTomorrows card) {
        super(card);
    }

    @Override
    public WitnessOfTomorrows copy() {
        return new WitnessOfTomorrows(this);
    }
}

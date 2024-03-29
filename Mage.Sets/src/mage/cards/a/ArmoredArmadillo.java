package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentToughnessValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmoredArmadillo extends CardImpl {

    public ArmoredArmadillo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ARMADILLO);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // {3}{W}: Armored Armadillo gets +X/+0 until end of turn, where X is its toughness.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                SourcePermanentToughnessValue.getInstance(), StaticValue.get(0), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{3}{W}")));
    }

    private ArmoredArmadillo(final ArmoredArmadillo card) {
        super(card);
    }

    @Override
    public ArmoredArmadillo copy() {
        return new ArmoredArmadillo(this);
    }
}

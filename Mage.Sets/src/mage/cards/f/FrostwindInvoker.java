package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class FrostwindInvoker extends CardImpl {

    public FrostwindInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), new ManaCostsImpl<>("{8}")));
    }

    private FrostwindInvoker(final FrostwindInvoker card) {
        super(card);
    }

    @Override
    public FrostwindInvoker copy() {
        return new FrostwindInvoker(this);
    }
}

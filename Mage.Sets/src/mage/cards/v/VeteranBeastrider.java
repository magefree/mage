package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeteranBeastrider extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("each creature you control");

    public VeteranBeastrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, untap each creature you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new UntapAllEffect(filter)));

        // {2}{G}{W}: Creatures you control get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostControlledEffect(
                1, 1, Duration.EndOfTurn, false
        ), new ManaCostsImpl<>("{2}{G}{W}")));
    }

    private VeteranBeastrider(final VeteranBeastrider card) {
        super(card);
    }

    @Override
    public VeteranBeastrider copy() {
        return new VeteranBeastrider(this);
    }
}

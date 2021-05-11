package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedcapRaiders extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("an untapped non-Human creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public RedcapRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Redcap Raiders attacks, you may tap an untapped non-Human creature you control. If you do, Redcap Raiders gets +1/+1 and gains trample until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("{this} gets +1/+1"),
                new TapTargetCost(new TargetControlledPermanent(filter))
        ).addEffect(new GainAbilitySourceEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn")), false));
    }

    private RedcapRaiders(final RedcapRaiders card) {
        super(card);
    }

    @Override
    public RedcapRaiders copy() {
        return new RedcapRaiders(this);
    }
}

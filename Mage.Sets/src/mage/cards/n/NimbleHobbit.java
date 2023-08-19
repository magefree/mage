package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimbleHobbit extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.FOOD, "Food");

    public NimbleHobbit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Nimble Hobbit attacks, you may sacrifice a Food or pay {2}{W}. When you do, tap target creature an opponent controls.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new OrCost("sacrifice a Food or pay {2}{W}",
                new SacrificeTargetCost(filter), new ManaCostsImpl<>("{2}{W}")
        ), "Pay the cost?")));
    }

    private NimbleHobbit(final NimbleHobbit card) {
        super(card);
    }

    @Override
    public NimbleHobbit copy() {
        return new NimbleHobbit(this);
    }
}

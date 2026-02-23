package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GravelgillScoundrel extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("another untapped creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public GravelgillScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever this creature attacks, you may tap another untapped creature you control. If you do, this creature can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new TapTargetCost(filter)
        )));
    }

    private GravelgillScoundrel(final GravelgillScoundrel card) {
        super(card);
    }

    @Override
    public GravelgillScoundrel copy() {
        return new GravelgillScoundrel(this);
    }
}

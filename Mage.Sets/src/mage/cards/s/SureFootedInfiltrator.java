package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SureFootedInfiltrator extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.ROGUE, "another untapped Rogue you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SureFootedInfiltrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Tap another untapped Rogue you control: Sure-Footed Infiltrator can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new TapTargetCost(new TargetControlledPermanent(filter))
        ));

        // Whenever Sure-Footed Infiltrator deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private SureFootedInfiltrator(final SureFootedInfiltrator card) {
        super(card);
    }

    @Override
    public SureFootedInfiltrator copy() {
        return new SureFootedInfiltrator(this);
    }
}

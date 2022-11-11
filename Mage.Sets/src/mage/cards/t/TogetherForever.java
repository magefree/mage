package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.delayed.WhenTargetDiesDelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801, jeffwadsworth
 */
public final class TogetherForever extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public TogetherForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");

        // When Together Forever enters the battlefield, support 2. (Put a +1/+1 counter on each of up to two target creatures.)
        this.addAbility(new SupportAbility(this, 2, false));

        // {1}: Choose target creature with a counter on it. When that creature dies this turn, return that card to its owner's hand.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(
                        new WhenTargetDiesDelayedTriggeredAbility(
                                new ReturnFromGraveyardToHandTargetEffect()
                                        .setText("return that card to its owner's hand"),
                                SetTargetPointer.CARD
                        ),
                        true, "Choose target creature with a counter on it. "
                ),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TogetherForever(final TogetherForever card) {
        super(card);
    }

    @Override
    public TogetherForever copy() {
        return new TogetherForever(this);
    }
}

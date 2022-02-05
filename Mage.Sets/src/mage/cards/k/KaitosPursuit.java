package mage.cards.k;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KaitosPursuit extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Ninjas and Rogues");

    static {
        filter.add(Predicates.or(
                SubType.NINJA.getPredicate(),
                SubType.ROGUE.getPredicate()
        ));
    }

    public KaitosPursuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player discards two cards. Ninjas and Rogues you control gain menace until end of turn.
        this.getSpellAbility().addEffect(new DiscardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new MenaceAbility(false), Duration.EndOfTurn, filter
        ));
    }

    private KaitosPursuit(final KaitosPursuit card) {
        super(card);
    }

    @Override
    public KaitosPursuit copy() {
        return new KaitosPursuit(this);
    }
}

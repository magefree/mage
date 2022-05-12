package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.MaxManaValueControlledPermanentPredicate;
import mage.target.TargetPlayer;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RiveteersCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent(
            "creature or planeswalker they control with the highest mana value " +
                    "among creatures and planeswalkers they control"
    );

    static {
        filter.add(MaxManaValueControlledPermanentPredicate.instance);
    }

    public RiveteersCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{R}{G}");

        // Choose one —
        // • Target opponent sacrifices a creature or planeswalker they control with the highest mana value among creatures and planeswalkers they control.
        this.getSpellAbility().addEffect(new SacrificeEffect(filter, 1, "target opponent"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Exile the top three cards of your library. Until your next end step, you may play those cards.
        this.getSpellAbility().addMode(new Mode(new ExileTopXMayPlayUntilEndOfTurnEffect(
                3, false, Duration.UntilYourNextEndStep
        )));

        // • Exile target player's graveyard.
        this.getSpellAbility().addMode(new Mode(new ExileGraveyardAllTargetPlayerEffect()).addTarget(new TargetPlayer()));
    }

    private RiveteersCharm(final RiveteersCharm card) {
        super(card);
    }

    @Override
    public RiveteersCharm copy() {
        return new RiveteersCharm(this);
    }
}

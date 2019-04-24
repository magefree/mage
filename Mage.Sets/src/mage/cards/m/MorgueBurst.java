
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class MorgueBurst extends CardImpl {

    public MorgueBurst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{R}");

        // Return target creature card from your graveyard to your hand. Morgue Burst deals damage to any target equal to the power of the card returned this way.
        this.getSpellAbility().addEffect(new MorgueBurstEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    public MorgueBurst(final MorgueBurst card) {
        super(card);
    }

    @Override
    public MorgueBurst copy() {
        return new MorgueBurst(this);
    }
}

class MorgueBurstEffect extends OneShotEffect {

    public MorgueBurstEffect() {
        super(Outcome.ReturnToHand);
    }

    public MorgueBurstEffect(final MorgueBurstEffect effect) {
        super(effect);
    }

    @Override
    public MorgueBurstEffect copy() {
        return new MorgueBurstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card != null) {
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                player.moveCards(card, Zone.HAND, source, game);
                int damage = card.getPower().getValue();
                Permanent creature = game.getPermanent(source.getTargets().get(1).getTargets().get(0));
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), game, false, true);
                    return true;
                }
                Player targetPlayer = game.getPlayer(source.getTargets().get(1).getTargets().get(0));
                if (targetPlayer != null) {
                    targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Return target creature card from your graveyard to your hand. Morgue Burst deals damage to any target equal to the power of the card returned this way");
        return sb.toString();
    }
}



package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class VengefulRebirth extends CardImpl {

    public VengefulRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}{G}");

        // Return target card from your graveyard to your hand. If you return a nonland card to your hand this way, {this} deals damage equal to that card's converted mana cost to any target
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new VengefulRebirthEffect());
        
        // Exile Vengeful Rebirth.
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public VengefulRebirth(final VengefulRebirth card) {
        super(card);
    }

    @Override
    public VengefulRebirth copy() {
        return new VengefulRebirth(this);
    }
}

class VengefulRebirthEffect extends OneShotEffect {

    public VengefulRebirthEffect() {
        super(Outcome.DrawCard);
        staticText = "Return target card from your graveyard to your hand. If you return a nonland card to your hand this way, {this} deals damage equal to that card's converted mana cost to any target";
    }

    public VengefulRebirthEffect(final VengefulRebirthEffect effect) {
        super(effect);
    }

    @Override
    public VengefulRebirthEffect copy() {
        return new VengefulRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = (Card)game.getObject(source.getFirstTarget());
        if (controller != null && card != null && controller.removeFromGraveyard(card, game)) {
            controller.moveCards(card, Zone.HAND, source, game);
            if (!card.isLand()) {
                int damage = card.getConvertedManaCost();                
                Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                if (permanent != null) {
                    permanent.damage(damage, source.getSourceId(), game, false, true);
                }
                Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
                if (targetPlayer != null) {
                    targetPlayer.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

}

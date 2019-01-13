
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class EbonyCharm extends CardImpl {

    public EbonyCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Choose one - Target opponent loses 1 life and you gain 1 life;
        this.getSpellAbility().addEffect(new EbonyCharmDrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        
        // or exile up to three target cards from a single graveyard; 
        Mode mode = new Mode();
        mode.addEffect(new EbonyCharmExileEffect());
        mode.addTarget((new TargetCardInASingleGraveyard(0, 3, new FilterCard("up to three target cards from a single graveyard"))));
        this.getSpellAbility().addMode(mode);
        
        // or target creature gains fear until end of turn.
        mode = new Mode();
        mode.addTarget(new TargetCreaturePermanent());
        mode.addEffect(new GainAbilityTargetEffect(FearAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addMode(mode);
    }

    public EbonyCharm(final EbonyCharm card) {
        super(card);
    }

    @Override
    public EbonyCharm copy() {
        return new EbonyCharm(this);
    }
}

class EbonyCharmDrainEffect extends OneShotEffect {

    EbonyCharmDrainEffect() {
        super(Outcome.Damage);
        staticText = "target opponent loses 1 life and you gain 1 life";
    }

    EbonyCharmDrainEffect(final EbonyCharmDrainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controllerPlayer = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controllerPlayer != null) {
            targetPlayer.damage(1, source.getSourceId(), game, false, true);
            controllerPlayer.gainLife(1, game, source);
        }
        return false;
    }

    @Override
    public EbonyCharmDrainEffect copy() {
        return new EbonyCharmDrainEffect(this);
    }

}

class EbonyCharmExileEffect extends OneShotEffect {

    public EbonyCharmExileEffect() {
            super(Outcome.Exile);
            this.staticText = "Exile up to three target cards from a single graveyard";
    }

    public EbonyCharmExileEffect(final EbonyCharmExileEffect effect) {
            super(effect);
    }

    @Override
    public EbonyCharmExileEffect copy() {
            return new EbonyCharmExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetID : source.getTargets().get(0).getTargets()) {
            Card card = game.getCard(targetID);
            if (card != null) {
                card.moveToExile(null, "", source.getSourceId(), game);
            }
        }
        return true;
    }
}

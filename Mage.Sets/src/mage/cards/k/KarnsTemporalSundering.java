package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetNonlandPermanent;

/**
 * @author JRHerlehy Created on 4/8/18.
 */
public final class KarnsTemporalSundering extends CardImpl {

    public KarnsTemporalSundering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Target player takes an extra turn after this one. Return up to one target nonland permanent to its owner's hand. Exile Karn's Temporal Sundering.
        this.getSpellAbility().addEffect(new KarnsTemporalSunderingEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent(0, 1, false));
        this.getSpellAbility().addEffect(ExileSpellEffect.getInstance());
    }

    public KarnsTemporalSundering(final KarnsTemporalSundering card) {
        super(card);
    }

    @Override
    public KarnsTemporalSundering copy() {
        return new KarnsTemporalSundering(this);
    }
}

class KarnsTemporalSunderingEffect extends OneShotEffect {

    public KarnsTemporalSunderingEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "Target player takes an extra turn after this one. Return up to one target nonland permanent to its owner's hand";
    }

    public KarnsTemporalSunderingEffect(final KarnsTemporalSunderingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        game.getState().getTurnMods().add(new TurnMod(source.getTargets().getFirstTarget(), false));

        Permanent returnPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());

        if (returnPermanent != null) {
            Card returnCard = returnPermanent.getMainCard();
            Player cardOwner = game.getPlayer(returnCard.getOwnerId());
            cardOwner.moveCards(returnCard, Zone.HAND, source, game);
        }

        return true;
    }

    @Override
    public KarnsTemporalSunderingEffect copy() {
        return new KarnsTemporalSunderingEffect(this);
    }
}

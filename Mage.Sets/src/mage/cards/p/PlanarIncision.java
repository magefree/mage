/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 * @author jeffwadsworth
 */
public final class PlanarIncision extends CardImpl {

    public PlanarIncision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target artifact or creature, then return it to the battlefield under its owner’s control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new PlanarIncisionEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private PlanarIncision(final PlanarIncision card) {
        super(card);
    }

    @Override
    public PlanarIncision copy() {
        return new PlanarIncision(this);
    }
}

class PlanarIncisionEffect extends OneShotEffect {

    PlanarIncisionEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target artifact or creature, then return it to the battlefield under its owner’s control with a +1/+1 counter on it";
    }

    private PlanarIncisionEffect(final PlanarIncisionEffect effect) {
        super(effect);
    }

    @Override
    public PlanarIncisionEffect copy() {
        return new PlanarIncisionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null
                && controller != null) {
            UUID exileId = CardUtil.getExileZoneId("planarIncisionExile" + source.toString(), game);
            if (controller.moveCardsToExile(permanent, source, game, true, exileId, "")) {
                Card exiledCard = game.getExile().getExileZone(exileId).get(permanent.getId(), game);
                if (exiledCard != null) {
                    Counters countersToAdd = new Counters();
                    countersToAdd.addCounter(CounterType.P1P1.createInstance());
                    game.setEnterWithCounters(exiledCard.getId(), countersToAdd);
                    return controller.moveCards(exiledCard, Zone.BATTLEFIELD, source, game, false, false, true, null);
                }
            }
        }
        return false;
    }
}

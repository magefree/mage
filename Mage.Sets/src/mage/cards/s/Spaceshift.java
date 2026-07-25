package mage.cards.s;

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
 *
 * @author muz
 */
public final class Spaceshift extends CardImpl {

    public Spaceshift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target artifact or creature, then return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.getSpellAbility().addEffect(new SpaceshiftEffect());
    }

    private Spaceshift(final Spaceshift card) {
        super(card);
    }

    @Override
    public Spaceshift copy() {
        return new Spaceshift(this);
    }
}

class SpaceshiftEffect extends OneShotEffect {

    SpaceshiftEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target artifact or creature, then return that card to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    private SpaceshiftEffect(final SpaceshiftEffect effect) {
        super(effect);
    }

    @Override
    public SpaceshiftEffect copy() {
        return new SpaceshiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            UUID exileId = CardUtil.getExileZoneId("SpaceshiftEffectExile" + source.toString(), game);
            if (controller.moveCardsToExile(permanent, source, game, true, exileId, "")) {
                if (game.getExile().getExileZone(exileId) != null) {
                    Card exiledCard = game.getExile().getExileZone(exileId).get(permanent.getId(), game);
                    if (exiledCard != null) {
                        Counters countersToAdd = new Counters();
                        countersToAdd.addCounter(CounterType.P1P1.createInstance());
                        game.setEnterWithCounters(exiledCard.getId(), countersToAdd);
                        return controller.moveCards(exiledCard, Zone.BATTLEFIELD, source, game, false, false, true, null);
                    }
                }
            }
        }
        return false;
    }
}

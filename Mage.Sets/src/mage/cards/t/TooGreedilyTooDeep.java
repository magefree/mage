package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class TooGreedilyTooDeep extends CardImpl {

    public TooGreedilyTooDeep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{R}");

        // Put target creature card from a graveyard onto the battlefield under your control. That creature deals damage equal to its power to each other creature.
        this.getSpellAbility().addEffect(new TooGreedilyTooDeepEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
    }

    private TooGreedilyTooDeep(final TooGreedilyTooDeep card) {
        super(card);
    }

    @Override
    public TooGreedilyTooDeep copy() {
        return new TooGreedilyTooDeep(this);
    }
}

class TooGreedilyTooDeepEffect extends OneShotEffect {

    TooGreedilyTooDeepEffect() {
        super(Outcome.Benefit);
        this.staticText = "put target creature card from a graveyard onto the battlefield under your control. That creature deals damage equal to its power to each other creature";
    }

    private TooGreedilyTooDeepEffect(final TooGreedilyTooDeepEffect effect) {
        super(effect);
    }

    @Override
    public TooGreedilyTooDeepEffect copy() {
        return new TooGreedilyTooDeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        game.getState().processAction(game);
        Permanent returnedCreature = game.getPermanent(card.getId());
        if (returnedCreature != null && returnedCreature.getPower().getValue() > 0) {
            for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                if (!creature.getId().equals(returnedCreature.getId())) {
                    creature.damage(returnedCreature.getPower().getValue(), returnedCreature.getId(), source, game, false, true);
                }
            }
        }
        return true;
    }
}

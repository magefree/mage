package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HelmOfObedience extends CardImpl {

    public HelmOfObedience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {X}, {T}: Target opponent puts cards from the top of their library into their graveyard until a creature card or X cards are put into that graveyard this way, whichever comes first. If a creature card is put into that graveyard this way, sacrifice Helm of Obedience and put that card onto the battlefield under your control. X can't be 0.
        VariableManaCost xCosts = new VariableManaCost(VariableCostType.NORMAL);
        xCosts.setMinX(1);
        Ability ability = new SimpleActivatedAbility(new HelmOfObedienceEffect(), xCosts);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private HelmOfObedience(final HelmOfObedience card) {
        super(card);
    }

    @Override
    public HelmOfObedience copy() {
        return new HelmOfObedience(this);
    }
}

class HelmOfObedienceEffect extends OneShotEffect {

    HelmOfObedienceEffect() {
        super(Outcome.Detriment);
        staticText = "Target opponent mills a card, then repeats this process until a creature card "
                + "or X cards have been put into their graveyard this way, whichever comes first. "
                + "If one or more creature cards were put into that graveyard this way, "
                + "sacrifice {this} and put one of them onto the battlefield under your control. X can't be 0";
    }

    private HelmOfObedienceEffect(final HelmOfObedienceEffect effect) {
        super(effect);
    }

    @Override
    public HelmOfObedienceEffect copy() {
        return new HelmOfObedienceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        int max = ManacostVariableValue.REGULAR.calculate(game, source, this);
        if (targetOpponent == null || controller == null || max < 1) {
            return false;
        }
        Cards cards = new CardsImpl();
        while (targetOpponent.getLibrary().hasCards()) {
            cards.addAll(targetOpponent.millCards(1, source, game));
            cards.retainZone(Zone.GRAVEYARD, game);
            if (cards.size() >= max || cards.count(StaticFilters.FILTER_CARD_CREATURE, game) > 0) {
                break;
            }
        }
        Card card;
        switch (cards.count(StaticFilters.FILTER_CARD_CREATURE, game)) {
            case 0:
                return true;
            case 1:
                card = cards
                        .getCards(StaticFilters.FILTER_CARD_CREATURE, game)
                        .stream()
                        .findFirst()
                        .orElse(null);
                break;
            default:
                TargetCardInGraveyard target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
                target.setNotTarget(true);
                controller.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
                card = game.getCard(target.getFirstTarget());
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.sacrifice(source, game);
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        return true;
    }

}

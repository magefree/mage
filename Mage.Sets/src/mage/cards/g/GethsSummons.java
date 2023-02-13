package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class GethsSummons extends CardImpl {

    public GethsSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Return up to one target creature card from your graveyard to the battlefield.
        //
        // Corrupted — For each opponent who has three or more poison counters as you cast this spell, put up
        // to one target creature card from that player's graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new GethsSummonsEffect());
        this.getSpellAbility().setTargetAdjuster(GethsSummonsAdjuster.instance);
    }

    private GethsSummons(final GethsSummons card) {
        super(card);
    }

    @Override
    public GethsSummons copy() {
        return new GethsSummons(this);
    }
}

enum GethsSummonsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();

        // up to one target creature card from your graveyard
        Target target = new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        ability.addTarget(target);

        // corrupted opponents' graveyards
        for (UUID opponentId : game.getOpponents(ability.getControllerId(), true)) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || opponent.getCounters().getCount(CounterType.POISON) < 3) {
                continue;
            }
            FilterCard filter = new FilterCard("creature card from " + opponent.getLogName() + "'s graveyard");
            filter.add(new OwnerIdPredicate(opponentId));
            filter.add(CardType.CREATURE.getPredicate());
            target = new TargetCardInOpponentsGraveyard(0, 1, filter);
            ability.addTarget(target);
        }
    }
}

class GethsSummonsEffect extends OneShotEffect {

    public GethsSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return up to one target creature card from your graveyard to the battlefield.<br>" +
                AbilityWord.CORRUPTED.formatWord() + "For each opponent who has three or more poison counters " +
                "as you cast this spell, put up to one target creature card from that player's graveyard onto " +
                "the battlefield under your control";
    }

    public GethsSummonsEffect(final GethsSummonsEffect effect) {
        super(effect);
    }

    @Override
    public GethsSummonsEffect copy() {
        return new GethsSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToBattlefield = new HashSet<>();
        for (Target target : source.getTargets()) {
            if (target instanceof TargetCardInYourGraveyard || target instanceof TargetCardInOpponentsGraveyard) {
                Card targetCard = game.getCard(target.getFirstTarget());
                if (targetCard != null) {
                    cardsToBattlefield.add(targetCard);
                }
            }
        }
        if (!cardsToBattlefield.isEmpty()) {
            controller.moveCards(cardsToBattlefield, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}

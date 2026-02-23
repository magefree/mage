package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathbonnetSprout extends TransformingDoubleFacedCard {

    private static final Condition condition = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_CREATURE);
    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public DeathbonnetSprout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FUNGUS}, "{G}",
                "Deathbonnet Hulk",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FUNGUS, SubType.HORROR}, "G");

        // Deathbonnet Sprout
        this.getLeftHalfCard().setPT(1, 1);

        // At the beginning of your upkeep, mill a card. Then if there are three or more creature cards in your graveyard, transform Deathbonnet Sprout.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MillCardsControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(new TransformSourceEffect(), condition,
                "Then if there are three or more creature cards in your graveyard, transform {this}"));
        this.getLeftHalfCard().addAbility(ability.addHint(hint));


        // Deathbonnet Hulk
        this.getRightHalfCard().setPT(3, 3);


        // At the beginning of your upkeep, you may exile a card from a graveyard. If a creature card was exiled this way, put a +1/+1 counter on Deathbonnet Hulk.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new DeathbonnetHulkEffect()));
    }

    private DeathbonnetSprout(final DeathbonnetSprout card) {
        super(card);
    }

    @Override
    public DeathbonnetSprout copy() {
        return new DeathbonnetSprout(this);
    }
}

class DeathbonnetHulkEffect extends OneShotEffect {
    DeathbonnetHulkEffect() {
        super(Outcome.Benefit);
        staticText = "you may exile a card from a graveyard. If a creature card was exiled this way, put a +1/+1 counter on {this}";
    }

    private DeathbonnetHulkEffect(final DeathbonnetHulkEffect effect) {
        super(effect);
    }

    @Override
    public DeathbonnetHulkEffect copy() {
        return new DeathbonnetHulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInGraveyard(0, 1);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        boolean creature = card.isCreature(game);
        player.moveCards(card, Zone.EXILED, source, game);
        if (!creature) {
            return true;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}

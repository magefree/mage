package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author muz
 */
public final class OriginOfTheAvengers extends CardImpl {

    public OriginOfTheAvengers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Scry 2.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ScryEffect(2));

        // II -- You may put a Hero creature card with mana value 3 or less from your hand onto the battlefield. If you don't, draw a card.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new OriginOfTheAvengersEffect());

        // III -- Put a +1/+1 counter on each creature you control.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new AddCountersAllEffect(
            CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ));

        this.addAbility(sagaAbility);
    }

    private OriginOfTheAvengers(final OriginOfTheAvengers card) {
        super(card);
    }

    @Override
    public OriginOfTheAvengers copy() {
        return new OriginOfTheAvengers(this);
    }
}

class OriginOfTheAvengersEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("Hero creature card with mana value 3 or less");

    static {
        filter.add(SubType.HERO.getPredicate());
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    OriginOfTheAvengersEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put a Hero creature card with mana value 3 or less from your hand " +
                "onto the battlefield. If you don't, draw a card";
    }

    private OriginOfTheAvengersEffect(final OriginOfTheAvengersEffect effect) {
        super(effect);
    }

    @Override
    public OriginOfTheAvengersEffect copy() {
        return new OriginOfTheAvengersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean putOntoBattlefield = false;
        if (player.chooseUse(
                Outcome.PutCreatureInPlay,
                "Put " + filter.getMessage() + " from your hand onto the battlefield?",
                source,
                game
        )) {
            TargetCardInHand target = new TargetCardInHand(filter);
            player.choose(Outcome.PutCreatureInPlay, target, source, game);
            Card card = game.getCard(target.getFirstTarget());
            putOntoBattlefield = card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (!putOntoBattlefield) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}

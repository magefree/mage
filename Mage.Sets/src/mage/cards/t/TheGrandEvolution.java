package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGrandEvolution extends CardImpl {

    public TheGrandEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.SAGA);
        this.color.setGreen(true);
        this.nightCard = true;

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- Mill ten cards. Put up to two creature cards from among the milled cards onto the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new TheGrandEvolutionEffect());

        // II -- Distribute seven +1/+1 counters among any number of target creatures you control.
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new DistributeCountersEffect(
                        CounterType.P1P1, 7,
                        "any number of target creatures you control"
                ), new TargetPermanentAmount(7, StaticFilters.FILTER_CONTROLLED_CREATURES)
        );

        // III -- Until end of turn, creatures you control gain "{1}: This creature fights target creature you don't control." Exile The Grand Evolution, then return it to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new FightTargetSourceEffect()
                        .setText("this creature fights target creature you don't control"),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_III,
                new GainAbilityControlledEffect(
                        ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("Until end of turn, creatures you control gain " +
                        "\"{1}: This creature fights target creature you don't control.\""),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.addAbility(sagaAbility);
    }

    private TheGrandEvolution(final TheGrandEvolution card) {
        super(card);
    }

    @Override
    public TheGrandEvolution copy() {
        return new TheGrandEvolution(this);
    }
}

class TheGrandEvolutionEffect extends OneShotEffect {

    TheGrandEvolutionEffect() {
        super(Outcome.Benefit);
        staticText = "mill ten cards. Put up to two creature cards from among the milled cards onto the battlefield";
    }

    private TheGrandEvolutionEffect(final TheGrandEvolutionEffect effect) {
        super(effect);
    }

    @Override
    public TheGrandEvolutionEffect copy() {
        return new TheGrandEvolutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(10, source, game);
        TargetCard target = new TargetCard(0, 2, Zone.ALL, StaticFilters.FILTER_CARD_CREATURE);
        target.setNotTarget(true);
        player.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}

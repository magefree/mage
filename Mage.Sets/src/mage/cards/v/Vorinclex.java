package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Vorinclex extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("Forest cards");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public Vorinclex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.PRAETOR}, "{3}{G}{G}",
                "The Grand Evolution",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "G"
        );

        // Vorinclex
        this.getLeftHalfCard().setPT(6, 6);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // When Vorinclex enters the battlefield, search your library for up to two Forest cards, reveal them, put them into your hand, then shuffle.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 2, filter), true
        )));

        // {6}{G}{G}: Exile Vorinclex, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{6}{G}{G}")
        ));

        // The Grand Evolution
        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I -- Mill ten cards. Put up to two creature cards from among the milled cards onto the battlefield.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, new TheGrandEvolutionEffect());

        // II -- Distribute seven +1/+1 counters among any number of target creatures you control.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_II, SagaChapter.CHAPTER_II,
                new DistributeCountersEffect(),
                new TargetCreaturePermanentAmount(7, StaticFilters.FILTER_CONTROLLED_CREATURES)
        );

        // III -- Until end of turn, creatures you control gain "{1}: This creature fights target creature you don't control." Exile The Grand Evolution, then return it to the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new FightTargetSourceEffect()
                        .setText("this creature fights target creature you don't control"),
                new GenericManaCost(1)
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                new GainAbilityControlledEffect(
                        ability, Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("Until end of turn, creatures you control gain " +
                        "\"{1}: This creature fights target creature you don't control.\""),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private Vorinclex(final Vorinclex card) {
        super(card);
    }

    @Override
    public Vorinclex copy() {
        return new Vorinclex(this);
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
        target.withNotTarget(true);
        player.choose(Outcome.PutCreatureInPlay, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}

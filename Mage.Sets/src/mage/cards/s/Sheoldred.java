package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class Sheoldred extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("nontoken creature or planeswalker");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Sheoldred(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.PRAETOR}, "{3}{B}{B}",
                "The True Scriptures",
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "B"
        );

        // Sheoldred
        this.getLeftHalfCard().setPT(4, 5);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // When Sheoldred enters the battlefield, each opponent sacrifices a nontoken creature or planeswalker.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeOpponentsEffect(filter)));

        // {4}{B}: Exile Sheoldred, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery and only if an opponent has eight or more cards in their graveyard.
        this.getLeftHalfCard().addAbility(new ActivateIfConditionActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{4}{B}"), CardsInOpponentGraveyardCondition.EIGHT
        ).setTiming(TimingRule.SORCERY).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // The True Scriptures
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I -- For each opponent, destroy up to one target creature or planeswalker that player controls.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_I, false,
                ability -> {
                    ability.addEffect(new DestroyTargetEffect().setTargetPointer(new EachTargetPointer())
                            .setText("for each opponent, destroy up to one target creature or planeswalker that player controls"));
                    ability.addTarget(new TargetCreatureOrPlaneswalker(0, 1));
                    ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
                }
        );

        // II -- Each opponent discards three cards, then mills three cards.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_II,
                new DiscardEachPlayerEffect(StaticValue.get(3), false, TargetController.OPPONENT),
                new MillCardsEachPlayerEffect(3, TargetController.OPPONENT).setText(", then mills three cards")
        );

        // III -- Put all creature cards from all graveyards onto the battlefield under your control. Exile The True Scriptures, then return it to the battlefield.
        sagaAbility.addChapterEffect(
                this.getRightHalfCard(), SagaChapter.CHAPTER_III,
                new SheoldredTrueScripturesEffect(),
                new ExileSourceAndReturnFaceUpEffect()
        );
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private Sheoldred(final Sheoldred card) {
        super(card);
    }

    @Override
    public Sheoldred copy() {
        return new Sheoldred(this);
    }
}

class SheoldredTrueScripturesEffect extends OneShotEffect {

    SheoldredTrueScripturesEffect() {
        super(Outcome.Benefit);
        staticText = "put all creature cards from all graveyards onto the battlefield under your control";
    }

    private SheoldredTrueScripturesEffect(final SheoldredTrueScripturesEffect effect) {
        super(effect);
    }

    @Override
    public SheoldredTrueScripturesEffect copy() {
        return new SheoldredTrueScripturesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game
                .getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(gy -> gy.getCards(StaticFilters.FILTER_CARD_CREATURE, game))
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}

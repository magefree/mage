package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PermanentsSacrificedWatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author muz
 */
public final class MagikIllyanaRasputin extends ModalDoubleFacedCard {

    public MagikIllyanaRasputin(UUID ownerId, CardSetInfo setInfo) {
        super(
            ownerId, setInfo,
            // Magik, Illyana Rasputin
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MUTANT, SubType.SORCERER, SubType.HERO}, "{1}{B}{R}",
            "Darkchild, Demon Uncaged",
            new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MUTANT, SubType.DEMON, SubType.SORCERER}, "{3}{B}{R}"
        );

        // 1.
        // Magik, Illyana Rasputin
        // Legendary Creature - Mutant Sorcerer Hero
        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(3));

        // At the beginning of your upkeep, exile the top card of your library. You may play that card this turn.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
            new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
        ));

        // At the beginning of your end step, if you've sacrificed two or more permanents this turn, you may transform Magik.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
            TargetController.YOU, new TransformSourceEffect(), true, MagikIllyanaRasputinCondition.instance
        );
        ability.addWatcher(new PermanentsSacrificedWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Darkchild, Demon Uncaged
        // Legendary Creature - Mutant Demon Sorcerer
        this.getRightHalfCard().setPT(new MageInt(4), new MageInt(4));

        // At the beginning of your upkeep, exile cards from the top of your library until you exile a nonland card. You may play those cards this turn.
        this.getRightHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new MagikIllyanaRasputinEffect()));

        // Pay 4 life: Darkchild gains indestructible until end of turn. Tap her.
        ability = new SimpleActivatedAbility(
            new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
            new PayLifeCost(4)
        );
        ability.addEffect(new TapSourceEffect().setText("Tap her"));
        this.getRightHalfCard().addAbility(ability);
    }

    private MagikIllyanaRasputin(final MagikIllyanaRasputin card) {
        super(card);
    }

    @Override
    public MagikIllyanaRasputin copy() {
        return new MagikIllyanaRasputin(this);
    }
}

enum MagikIllyanaRasputinCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
            .getState()
            .getWatcher(PermanentsSacrificedWatcher.class)
            .getThisTurnSacrificedPermanents(source.getControllerId())
            .size() >= 2;
    }

    @Override
    public String toString() {
        return "you've sacrificed two or more permanents this turn";
    }
}

class MagikIllyanaRasputinEffect extends OneShotEffect {

    MagikIllyanaRasputinEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
            "You may play those cards this turn";
    }

    private MagikIllyanaRasputinEffect(final MagikIllyanaRasputinEffect effect) {
        super(effect);
    }

    @Override
    public MagikIllyanaRasputinEffect copy() {
        return new MagikIllyanaRasputinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = new HashSet<>();
        for (Card card : controller.getLibrary().getCards(game)) {
            cards.add(card);
            if (!card.isLand(game)) {
                break;
            }
        }
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
            game, source, cards, TargetController.YOU, Duration.EndOfTurn, false, false, false
        );
    }
}

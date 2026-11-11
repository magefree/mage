package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.UUID;

public final class TyphoidMaryFractured extends CardImpl {

    public TyphoidMaryFractured(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT, SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        AttacksTriggeredAbility ability = new AttacksTriggeredAbility(new TyphoidMaryFracturedEffect());
        ability.addWatcher(new DiscardedCardWatcher());
        this.addAbility(ability);
    }

    private TyphoidMaryFractured(final TyphoidMaryFractured card) {
        super(card);
    }

    @Override
    public TyphoidMaryFractured copy() {
        return new TyphoidMaryFractured(this);
    }
}

class TyphoidMaryFracturedEffect extends OneShotEffect {

    private static final String MARY = "Mary";
    private static final String TYPHOID_MARY = "Typhoid Mary";
    private static final String BLOODY_MARY = "Bloody Mary";

    TyphoidMaryFracturedEffect() {
        super(Outcome.Benefit);
        staticText = "choose one at random. If you discarded a card this turn, you choose one instead";
    }

    private TyphoidMaryFracturedEffect(final TyphoidMaryFracturedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, mage.abilities.Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        boolean discardedThisTurn = DiscardedCardWatcher.checkPlayerDiscarded(controller.getId(), game);
        String choice;
        if (discardedThisTurn) {
            ChoiceImpl modeChoice = new ChoiceImpl(true);
            modeChoice.setMessage("Choose one");
            modeChoice.setChoices(new LinkedHashSet<>(Arrays.asList(MARY, TYPHOID_MARY, BLOODY_MARY)));
            if (!controller.choose(outcome, modeChoice, game)) {
                return false;
            }
            choice = modeChoice.getChoice();
        } else {
            String[] modes = {MARY, TYPHOID_MARY, BLOODY_MARY};
            choice = modes[RandomUtil.nextInt(modes.length)];
        }

        game.informPlayers(controller.getLogName() + " chose " + choice + " for Typhoid Mary, Fractured");
        switch (choice) {
            case MARY:
                return new CreateTokenEffect(new TreasureToken()).apply(game, source);
            case TYPHOID_MARY:
                return new DrawCardSourceControllerEffect(1).apply(game, source);
            case BLOODY_MARY:
                for (UUID opponentId : game.getOpponents(controller.getId())) {
                    LoseLifeTargetEffect loseLife = new LoseLifeTargetEffect(2);
                    loseLife.setTargetPointer(new FixedTarget(opponentId));
                    loseLife.apply(game, source);
                }
                return new GainLifeEffect(2).apply(game, source);
            default:
                return false;
        }
    }

    @Override
    public TyphoidMaryFracturedEffect copy() {
        return new TyphoidMaryFracturedEffect(this);
    }
}

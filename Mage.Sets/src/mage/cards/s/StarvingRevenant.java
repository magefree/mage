package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class StarvingRevenant extends CardImpl {

    public StarvingRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Starving Revenant enters the battlefield, surveil 2. Then for each card you put on top of your library, you draw a card and you lose 3 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new StarvingRevenantEffect()));

        // Descend 8 -- Whenever you draw a card, if there are eight or more permanent cards in your graveyard, target opponent loses 1 life and you gain 1 life.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DrawCardControllerTriggeredAbility(new LoseLifeTargetEffect(1), false),
                DescendCondition.EIGHT, "Whenever you draw a card, "
                + "if there are eight or more permanent cards in your graveyard, "
                + "target opponent loses 1 life and you gain 1 life."
        );
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(DescendCondition.getHint()).setAbilityWord(AbilityWord.DESCEND_8));
    }

    private StarvingRevenant(final StarvingRevenant card) {
        super(card);
    }

    @Override
    public StarvingRevenant copy() {
        return new StarvingRevenant(this);
    }
}

class StarvingRevenantEffect extends OneShotEffect {

    StarvingRevenantEffect() {
        super(Outcome.Benefit);
        staticText = "surveil 2. Then for each card you put on top of your library, you draw a card and you lose 3 life";
    }

    private StarvingRevenantEffect(final StarvingRevenantEffect effect) {
        super(effect);
    }

    @Override
    public StarvingRevenantEffect copy() {
        return new StarvingRevenantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int amountOnTop = controller.doSurveil(2, source, game).getNumberPutOnTop();
        for (int i = 0; i < amountOnTop; ++i) {
            controller.drawCards(1, source, game);
            controller.loseLife(3, game, source, false);
        }
        return true;
    }

}
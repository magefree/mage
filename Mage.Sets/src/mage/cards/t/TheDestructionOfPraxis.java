package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.KlingonWarriorToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class TheDestructionOfPraxis extends CardImpl {

    public TheDestructionOfPraxis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Strange new worlds -- All creatures get -X/-X until end of turn, where X is the number of differently named lands you control. If X is 6 or more, create two 2/1 red Klingon Warrior creature tokens with haste.
        this.getSpellAbility().addEffect(new TheDestructionOfPraxisEffect());
        this.getSpellAbility().setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS);
        this.getSpellAbility().addHint(StrangeNewWorldsHint.instance);
    }

    private TheDestructionOfPraxis(final TheDestructionOfPraxis card) {
        super(card);
    }

    @Override
    public TheDestructionOfPraxis copy() {
        return new TheDestructionOfPraxis(this);
    }
}

class TheDestructionOfPraxisEffect extends OneShotEffect {

    TheDestructionOfPraxisEffect() {
        super(Outcome.UnboostCreature);
        staticText = "all creatures get -X/-X until end of turn, "
            + "where X is the number of differently named lands you control. "
            + "If X is 6 or more, create two 2/1 red Klingon Warrior creature tokens with haste";
    }

    private TheDestructionOfPraxisEffect(final TheDestructionOfPraxisEffect effect) {
        super(effect);
    }

    @Override
    public TheDestructionOfPraxisEffect copy() {
        return new TheDestructionOfPraxisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = StrangeNewWorldsValue.instance.calculate(game, source, this);
        game.addEffect(new BoostAllEffect((-1 * amount), (-1 * amount), Duration.EndOfTurn), source);
        if (amount >= 6) {
            new CreateTokenEffect(new KlingonWarriorToken(), 2).apply(game, source);
        }
        return true;
    }
}

package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jimga150
 */
public final class Celebr8000 extends CardImpl {

    public Celebr8000(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.CLOWN);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, roll two six-sided dice.
        // For each result of 1, Celebr-8000 gets +1/+1 until end of turn.
        // For each other result, it gains the indicated ability until end of turn.
        // If you rolled doubles, it also gains double strike until end of turn.
        // * 2 -- menace
        // * 3 -- vigilance
        // * 4 -- lifelink
        // * 5 -- flying
        // * 6 -- indestructible
        this.addAbility(
                new BeginningOfCombatTriggeredAbility(new Celebr8000Effect(), TargetController.YOU, false));
    }

    private Celebr8000(final Celebr8000 card) {
        super(card);
    }

    @Override
    public Celebr8000 copy() {
        return new Celebr8000(this);
    }
}

class Celebr8000Effect extends OneShotEffect {

    private static final ContinuousEffect[] d6_effects = {
            /* 0 */null,
            /* 1 */new BoostSourceEffect(1, 1, Duration.EndOfTurn),
            /* 2 */new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn),
            /* 3 */new GainAbilitySourceEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn),
            /* 4 */new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
            /* 5 */new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
            /* 6 */new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
    };

    private static final ContinuousEffect doubles_effect =
            new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn);

    Celebr8000Effect() {
        super(Outcome.Benefit);
        setText("roll two six-sided dice. For each result of 1, {this} gets +1/+1 until end of turn. " +
                "For each other result, it gains the indicated ability until end of turn. " +
                "If you rolled doubles, it also gains double strike until end of turn.<br>" +
                "&bull; 2 — menace<br>" +
                "&bull; 3 — vigilance<br>" +
                "&bull; 4 — lifelink<br>" +
                "&bull; 5 — flying<br>" +
                "&bull; 6 — indestructible");
    }

    private Celebr8000Effect(final Celebr8000Effect effect) {
        super(effect);
    }

    @Override
    public Celebr8000Effect copy() {
        return new Celebr8000Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        List<Integer> results = player.rollDice(outcome, source, game, 6, 2, 0);

        int roll1 = results.get(0);
        int roll2 = results.get(1);

        if (roll1 >= 1 && roll1 <= 6) {
            game.addEffect(d6_effects[roll1], source);
        }
        if (roll2 >= 1 && roll2 <= 6) {
            game.addEffect(d6_effects[roll2], source);
        }
        if (roll1 == roll2){
            game.addEffect(doubles_effect, source);
        }
        return true;
    }

}

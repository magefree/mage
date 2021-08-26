package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class SparkFiend extends CardImpl {

    public SparkFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Spark Fiend enters the battlefield, roll two six-sided dice. If you rolled 2, 3, or 12, sacrifice Spark Fiend. If you rolled 7 or 11, don't roll dice for Spark Fiend during any of your following upkeeps. If you rolled any other total, note that total.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SparkFiendEffect(), false));

        // At the beginning of your upkeep, roll two six-sided dice. If you rolled 7, sacrifice Spark Fiend. If you roll the noted total, don't roll dice for Spark Fiend during any of your following upkeeps. Otherwise, do nothing.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SparkFiendUpkeepEffect(), TargetController.YOU, false));
    }

    private SparkFiend(final SparkFiend card) {
        super(card);
    }

    @Override
    public SparkFiend copy() {
        return new SparkFiend(this);
    }
}

class SparkFiendEffect extends OneShotEffect {

    public SparkFiendEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "roll two six-sided dice. If you rolled 2, 3, or 12, sacrifice Spark Fiend. If you rolled 7 or 11, don't roll dice for Spark Fiend during any of your following upkeeps. If you rolled any other total, note that total";
    }

    public SparkFiendEffect(final SparkFiendEffect effect) {
        super(effect);
    }

    @Override
    public SparkFiendEffect copy() {
        return new SparkFiendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int roll = controller.rollDice(outcome, source, game, 6, 2, 0).stream().mapToInt(x -> x).sum();
            MageObject mageObject = game.getObject(source.getSourceId());
            if (mageObject instanceof Permanent) {
                Permanent sourcePermanent = (Permanent) mageObject;
                if (roll == 2 || roll == 3 || roll == 12) {
                    // sacrifice
                    sourcePermanent.sacrifice(source, game);
                } else if (roll == 7 || roll == 11) {
                    // don't roll again
                    game.getState().setValue("SparkFiend" + source.getSourceId().toString(), 0);
                    sourcePermanent.addInfo("roll counter", CardUtil.addToolTipMarkTags(""), game); // might apply if this ability was copied
                } else {
                    // note that total
                    game.getState().setValue("SparkFiend" + source.getSourceId().toString(), roll);
                    sourcePermanent.addInfo("roll counter", CardUtil.addToolTipMarkTags("Noted roll: " + roll), game);
                }
            }
            return true;
        }
        return false;
    }
}

class SparkFiendUpkeepEffect extends OneShotEffect {

    public SparkFiendUpkeepEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "roll two six-sided dice. If you rolled 7, sacrifice Spark Fiend. If you roll the noted total, don't roll dice for Spark Fiend during any of your following upkeeps. Otherwise, do nothing";
    }

    public SparkFiendUpkeepEffect(final SparkFiendUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public SparkFiendUpkeepEffect copy() {
        return new SparkFiendUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getState().getValue("SparkFiend" + source.getSourceId().toString()) != null
                    && (Integer) game.getState().getValue("SparkFiend" + source.getSourceId().toString()) != 0) {
                int roll = controller.rollDice(outcome, source, game, 6, 2, 0).stream().mapToInt(x -> x).sum();
                MageObject mageObject = game.getObject(source.getSourceId());
                if (mageObject instanceof Permanent) {
                    Permanent sourcePermanent = (Permanent) mageObject;
                    if (roll == 7) {
                        // sacrifice
                        sourcePermanent.sacrifice(source, game);
                    } else if (roll == (Integer) game.getState().getValue("SparkFiend" + source.getSourceId().toString())) {
                        // don't roll again
                        game.getState().setValue("SparkFiend" + source.getSourceId().toString(), 0);
                        sourcePermanent.addInfo("roll counter", CardUtil.addToolTipMarkTags(""), game);
                    } // otherwise, do nothing
                }
            }
            return true;
        }
        return false;
    }
}

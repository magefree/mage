
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.PermanentsEnteredBattlefieldWatcher;

/**
 *
 * @author LevelX2
 */
public final class EpharaGodOfThePolis extends CardImpl {

    public EpharaGodOfThePolis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{W}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // As long as your devotion to white and blue is less than seven, Ephara isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.W, ColoredManaSymbol.U), 7);
        effect.setText("As long as your devotion to white and blue is less than seven, Ephara isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // At the beginning of each upkeep, if you had another creature enter the battlefield under your control last turn, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), TargetController.ANY, false, false),
                HadAnotherCreatureEnterTheBattlefieldCondition.instance,
                "At the beginning of each upkeep, if you had another creature enter the battlefield under your control last turn, draw a card."),
                new PermanentsEnteredBattlefieldWatcher());

    }

    public EpharaGodOfThePolis(final EpharaGodOfThePolis card) {
        super(card);
    }

    @Override
    public EpharaGodOfThePolis copy() {
        return new EpharaGodOfThePolis(this);
    }
}

enum HadAnotherCreatureEnterTheBattlefieldCondition implements Condition {

    instance;



    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        PermanentsEnteredBattlefieldWatcher watcher = game.getState().getWatcher(PermanentsEnteredBattlefieldWatcher.class);
        return sourcePermanent != null
                && watcher != null
                && watcher.anotherCreatureEnteredBattlefieldUnderPlayersControlLastTurn(sourcePermanent, game);
    }
}

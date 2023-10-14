package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author weirddan455
 */
public final class IngaRuneEyes extends CardImpl {

    public IngaRuneEyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Inga Rune-Eyes enters the battlefield, scry 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(3)));

        // When Inga Rune-Eyes dies, draw three cards if three or more creatures died this turn.
        this.addAbility(new DiesSourceTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(3), IngaRuneEyesCondition.instance
        ).setText("draw three cards if three or more creatures died this turn")));
    }

    private IngaRuneEyes(final IngaRuneEyes card) {
        super(card);
    }

    @Override
    public IngaRuneEyes copy() {
        return new IngaRuneEyes(this);
    }
}

enum IngaRuneEyesCondition implements Condition {

    instance;

    @Override
    public boolean apply (Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        if (watcher != null) {
            return watcher.getAmountOfCreaturesDiedThisTurn() > 2;
        }
        return false;
    }
}

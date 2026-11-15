package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.CreaturesDiedWatcher;

import java.util.UUID;

/**
 * @author nandmp
 */
public final class DarklightPhoenix extends CardImpl {

    public DarklightPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying, haste
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // At the beginning of combat on your turn, if two or more creatures died this turn, return this card from your graveyard to the battlefield.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                Zone.GRAVEYARD, TargetController.YOU,
                new ReturnSourceFromGraveyardToBattlefieldEffect(), false
        ).withInterveningIf(DarklightPhoenixCondition.instance));
    }

    private DarklightPhoenix(final DarklightPhoenix card) {
        super(card);
    }

    @Override
    public DarklightPhoenix copy() {
        return new DarklightPhoenix(this);
    }
}

enum DarklightPhoenixCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CreaturesDiedWatcher watcher = game.getState().getWatcher(CreaturesDiedWatcher.class);
        return watcher != null && watcher.getAmountOfCreaturesDiedThisTurn() >= 2;
    }

    @Override
    public String toString() {
        return "two or more creatures died this turn";
    }
}

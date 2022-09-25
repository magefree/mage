package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.CreatureEnteredControllerWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhalfirinDecoy extends CardImpl {

    public ZhalfirinDecoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Tap target creature. Activate this ability only if you had a creature enter the battlefield under your control this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TapTargetEffect(),
                new TapSourceCost(), ZhalfirinDecoyCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new CreatureEnteredControllerWatcher());
    }

    private ZhalfirinDecoy(final ZhalfirinDecoy card) {
        super(card);
    }

    @Override
    public ZhalfirinDecoy copy() {
        return new ZhalfirinDecoy(this);
    }
}

enum ZhalfirinDecoyCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CreatureEnteredControllerWatcher.enteredCreatureForPlayer(source.getControllerId(), game);
    }

    @Override
    public String toString() {
        return "you had a creature enter the battlefield under your control this turn";
    }
}

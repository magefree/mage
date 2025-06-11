package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.CombatDamageDealtToYouTriggeredAbility;
import mage.abilities.common.PlayerAttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.Objects;
import java.util.UUID;

/**
 * @author alexander-novo
 */
public final class NornsDecree extends CardImpl {

    public NornsDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever one or more creatures an opponent controls deal combat damage to you, that opponent gets a poison counter.
        this.addAbility(new CombatDamageDealtToYouTriggeredAbility(
                Zone.BATTLEFIELD,
                new AddPoisonCounterTargetEffect(1).setText("that opponent gets a poison counter"),
                true, false
        ));

        // Whenever a player attacks, if one or more players being attacked are poisoned, the attacking player draws a card.
        this.addAbility(new PlayerAttacksTriggeredAbility(
                new DrawCardTargetEffect(1).setText("the attacking player draws a card"), true
        ).withInterveningIf(NornsDecreeCondition.instance));
    }

    private NornsDecree(final NornsDecree card) {
        super(card);
    }

    @Override
    public NornsDecree copy() {
        return new NornsDecree(this);
    }

}

enum NornsDecreeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getCombat()
                .getGroups()
                .stream()
                .map(CombatGroup::getDefenderId)
                .filter(Objects::nonNull)
                .distinct()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .anyMatch(player -> player.getCountersCount(CounterType.POISON) > 0);
    }

    @Override
    public String toString() {
        return "one or more players being attacked are poisoned";
    }
}

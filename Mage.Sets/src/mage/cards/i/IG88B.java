package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Styxo
 */
public final class IG88B extends CardImpl {

    public IG88B(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // <i>Bounty</i> &mdash; Whenever IF-88B deals combat damage to a player, that player loses life equal to the number of bounty counters on creatures they control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new LoseLifeTargetEffect(new CountersOnDefendingPlayerCreaturesCount(CounterType.BOUNTY))
                        .setText("that player loses life equal to the number of bounty counters on creatures they control"),
                false,
                true).withFlavorWord("Bounty")
        );

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private IG88B(final IG88B card) {
        super(card);
    }

    @Override
    public IG88B copy() {
        return new IG88B(this);
    }
}

class CountersOnDefendingPlayerCreaturesCount implements DynamicValue {

    private CounterType counterType;

    public CountersOnDefendingPlayerCreaturesCount(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        UUID defender = game.getCombat().getDefendingPlayerId(sourceAbility.getSourceId(), game);
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, defender, game)) {
            count += permanent.getCounters(game).getCount(counterType);
        }
        return count;
    }

    @Override
    public CountersOnDefendingPlayerCreaturesCount copy() {
        return new CountersOnDefendingPlayerCreaturesCount(counterType);
    }

    @Override
    public String getMessage() {
        return "the number of " + counterType.getName() + " counters on creatures they control";
    }
}

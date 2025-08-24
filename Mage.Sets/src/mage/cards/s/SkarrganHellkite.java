package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RiotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkarrganHellkite extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.P1P1);

    public SkarrganHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Riot
        this.addAbility(new RiotAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{R}: Skarrgan Hellkite deals 2 damage divided as you choose among one or two targets. Activate this ability only if Skarrgan Hellkite has a +1/+1 counter on it.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DamageMultiEffect(), new ManaCostsImpl<>("{3}{R}"), condition
        ).withConditionText("activate only if {this} has a +1/+1 counter on it");
        ability.addTarget(new TargetAnyTargetAmount(2));
        this.addAbility(ability);
    }

    private SkarrganHellkite(final SkarrganHellkite card) {
        super(card);
    }

    @Override
    public SkarrganHellkite copy() {
        return new SkarrganHellkite(this);
    }
}

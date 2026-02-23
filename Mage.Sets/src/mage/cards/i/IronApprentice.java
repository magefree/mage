package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.condition.common.SourceHasCountersCondition;
import mage.abilities.effects.common.PutSavedPermanentCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IronApprentice extends CardImpl {

    public IronApprentice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Iron Apprentice enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(1)));

        // When Iron Apprentice dies, if it had counters on it, put those counters on target creature you control.
        Ability ability = new DiesSourceTriggeredAbility(new PutSavedPermanentCountersTargetEffect("permanentLeftBattlefield"))
                .withTriggerCondition(SourceHasCountersCondition.instance) // don't want to check intervening if on resolution
                .setTriggerPhrase("When {this} dies, ");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private IronApprentice(final IronApprentice card) {
        super(card);
    }

    @Override
    public IronApprentice copy() {
        return new IronApprentice(this);
    }
}

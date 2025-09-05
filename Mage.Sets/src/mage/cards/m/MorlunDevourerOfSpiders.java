package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class MorlunDevourerOfSpiders extends CardImpl {

    public MorlunDevourerOfSpiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Morlun enters with X +1/+1 counters on him.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));

        // When Morlun enters, he deals X damage to target opponent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(GetXValue.instance, "he"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MorlunDevourerOfSpiders(final MorlunDevourerOfSpiders card) {
        super(card);
    }

    @Override
    public MorlunDevourerOfSpiders copy() {
        return new MorlunDevourerOfSpiders(this);
    }
}

package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostJitte extends CardImpl {

    public LostJitte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage, put a charge counter on Lost Jitte.
        this.addAbility(new DealsCombatDamageEquippedTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance())));

        // Remove a charge counter from Lost Jitte: Choose one --
        // * Untap target land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetLandPermanent());

        // * Target creature can't block this turn.
        ability.addMode(new Mode(new CantBlockTargetEffect(Duration.EndOfTurn)).addTarget(new TargetCreaturePermanent()));

        // * Put a +1/+1 counter on equipped creature.
        ability.addMode(new Mode(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "equipped creature")));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private LostJitte(final LostJitte card) {
        super(card);
    }

    @Override
    public LostJitte copy() {
        return new LostJitte(this);
    }
}

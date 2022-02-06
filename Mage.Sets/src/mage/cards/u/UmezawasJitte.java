package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsCombatDamageEquippedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class UmezawasJitte extends CardImpl {

    public UmezawasJitte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals combat damage, put two charge counters on Umezawa's Jitte.
        this.addAbility(new DealsCombatDamageEquippedTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance(2))
        ));

        // Remove a charge counter from Umezawa's Jitte: Choose one &mdash; Equipped creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostEquippedEffect(2, 2, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance()));

        // Target creature gets -1/-1 until end of turn.
        Mode mode = new Mode();
        mode.addEffect(new BoostTargetEffect(-1, -1, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        ability.addMode(mode);

        // You gain 2 life.
        mode = new Mode();
        mode.addEffect(new GainLifeEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private UmezawasJitte(final UmezawasJitte card) {
        super(card);
    }

    @Override
    public UmezawasJitte copy() {
        return new UmezawasJitte(this);
    }
}

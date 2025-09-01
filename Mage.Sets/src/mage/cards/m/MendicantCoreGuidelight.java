package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Jmlundeen
 */
public final class MendicantCoreGuidelight extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS);

    public MendicantCoreGuidelight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Mendicant Core's power is equal to the number of artifacts you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetBasePowerSourceEffect(xValue)));
        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Max speed -- Whenever you cast an artifact spell, you may pay {1}. If you do, copy it.
        Effect copyEffect = new CopyTargetStackObjectEffect(true)
                .setText("copy it. <i>(The copy becomes a token.)</i>");
        Effect doIfEffect = new DoIfCostPaid(copyEffect, new ManaCostsImpl<>("{1}"));
        Ability ability = new SpellCastControllerTriggeredAbility(doIfEffect, StaticFilters.FILTER_SPELL_AN_ARTIFACT, false, SetTargetPointer.SPELL);
        this.addAbility(new MaxSpeedAbility(ability));
    }

    private MendicantCoreGuidelight(final MendicantCoreGuidelight card) {
        super(card);
    }

    @Override
    public MendicantCoreGuidelight copy() {
        return new MendicantCoreGuidelight(this);
    }
}

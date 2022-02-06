
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureAttachedTriggeredAbility;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.condition.common.SourceRemainsInZoneCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class NekoTe extends CardImpl {

    public NekoTe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature deals damage to a creature, tap that creature. That creature doesn't untap during its controller's untap step for as long as Neko-Te remains on the battlefield.
        ContinuousRuleModifyingEffect skipUntapEffect = new DontUntapInControllersUntapStepTargetEffect(Duration.WhileOnBattlefield);
        skipUntapEffect.setText("That creature doesn't untap during its controller's untap step for as long as {this} remains on the battlefield");
        ConditionalContinuousRuleModifyingEffect effect = new ConditionalContinuousRuleModifyingEffect(skipUntapEffect, 
                new SourceRemainsInZoneCondition(Zone.BATTLEFIELD));
        Ability ability = new DealsDamageToACreatureAttachedTriggeredAbility(new TapTargetEffect("tap that creature"), false, "equipped creature", false, true);
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature deals damage to a player, that player loses 1 life.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new LoseLifeTargetEffect(1), "equipped creature", false, true, false));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(2), false));
    }

    private NekoTe(final NekoTe card) {
        super(card);
    }

    @Override
    public NekoTe copy() {
        return new NekoTe(this);
    }
}

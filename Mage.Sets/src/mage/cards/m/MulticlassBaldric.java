package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.FullPartyCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalPreventionEffect;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.hint.common.PartyCountHint;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;
import mage.abilities.costs.mana.GenericManaCost;
import mage.constants.Outcome;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class MulticlassBaldric extends CardImpl {

    private static final Condition condition1
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.CLERIC));
    private static final Condition condition2
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.ROGUE));
    private static final Condition condition3
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.WARRIOR));
    private static final Condition condition4
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.WIZARD));
    private static final Hint hint1
            = new ConditionHint(condition1, "You control a Cleric");
    private static final Hint hint2
            = new ConditionHint(condition2, "You control a Rogue");
    private static final Hint hint3
            = new ConditionHint(condition3, "You control a Warrior");
    private static final Hint hint4
            = new ConditionHint(condition4, "You control a Wizard");

    public MulticlassBaldric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has lifelink if you control a Cleric, deathtouch if you control a Rogue, haste if you control a Warrior, and flying if you control a Wizard.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(
                        LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT
                ), condition1, "equipped creature has lifelink if you control a Cleric"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.EQUIPMENT
        ), condition2, ", deathtouch if you control a Rogue"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                HasteAbility.getInstance(), AttachmentType.EQUIPMENT
        ), condition3, ", haste if you control a Warrior"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ), condition4, ", and flying if you control a Wizard"));
        this.addAbility(ability.addHint(hint1).addHint(hint2).addHint(hint3).addHint(hint4));

        // As long as you have a full party, prevent all damage that would be dealt to equipped creature.
        this.addAbility(new SimpleStaticAbility(new ConditionalPreventionEffect(
                new PreventDamageToAttachedEffect(
                        Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, false
                ), FullPartyCondition.instance, "as long as you have a full party, " +
                "prevent all damage that would be dealt to equipped creature"
        )).addHint(PartyCountHint.instance));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private MulticlassBaldric(final MulticlassBaldric card) {
        super(card);
    }

    @Override
    public MulticlassBaldric copy() {
        return new MulticlassBaldric(this);
    }
}

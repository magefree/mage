package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.effects.common.combat.ReselectDefenderAttackedByTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PortalMage extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false);

    public PortalMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Portal Mage enters the battlefield during the declare attackers step, you may reselect which player or planeswalker target attacking creature is attacking.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new ReselectDefenderAttackedByTargetEffect(true), true
        ).withTriggerCondition(condition);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private PortalMage(final PortalMage card) {
        super(card);
    }

    @Override
    public PortalMage copy() {
        return new PortalMage(this);
    }
}

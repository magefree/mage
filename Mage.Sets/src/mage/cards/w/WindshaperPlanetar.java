package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.effects.common.combat.ReselectDefenderAttackedByTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;

import java.util.UUID;

public final class WindshaperPlanetar extends CardImpl {

    private static final Condition condition = new IsStepCondition(PhaseStep.DECLARE_ATTACKERS, false);

    public WindshaperPlanetar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Windshaper Planetar enters the battlefield during the declare attackers step, for each attacking creature, you may reselect which player or planeswalker that creature is attacking.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ReselectDefenderAttackedByTargetEffect(true, true), true
        ).withTriggerCondition(condition));
    }

    private WindshaperPlanetar(final WindshaperPlanetar card) {
        super(card);
    }

    @Override
    public WindshaperPlanetar copy() {
        return new WindshaperPlanetar(this);
    }
}

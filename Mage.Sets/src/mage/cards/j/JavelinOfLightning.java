package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JavelinOfLightning extends CardImpl {

    public JavelinOfLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Javelin of Lightning enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // As long as it's your turn, equipped creature gets +2/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 0), MyTurnCondition.instance,
                "as long as it's your turn, equipped creature gets +2/+0"));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ), MyTurnCondition.instance, "and has first strike"));
        this.addAbility(ability.addHint(MyTurnHint.instance));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private JavelinOfLightning(final JavelinOfLightning card) {
        super(card);
    }

    @Override
    public JavelinOfLightning copy() {
        return new JavelinOfLightning(this);
    }
}

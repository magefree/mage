package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class QuickDrawKatana extends CardImpl {

    public QuickDrawKatana(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // During your turn, equipped creature gets +2/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(2, 0), MyTurnCondition.instance,
                "during your turn, equipped creature gets +2/+0"
        ));
        ability.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ), MyTurnCondition.instance, "and has first strike"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private QuickDrawKatana(final QuickDrawKatana card) {
        super(card);
    }

    @Override
    public QuickDrawKatana copy() {
        return new QuickDrawKatana(this);
    }
}

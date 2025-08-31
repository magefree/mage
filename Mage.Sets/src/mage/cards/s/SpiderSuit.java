package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderSuit extends CardImpl {

    public SpiderSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and is a Spider Hero in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.SPIDER, AttachmentType.EQUIPMENT
        ).setText("and is a Spider"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.HERO, AttachmentType.EQUIPMENT
        ).setText("Hero in addition to its other types"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private SpiderSuit(final SpiderSuit card) {
        super(card);
    }

    @Override
    public SpiderSuit copy() {
        return new SpiderSuit(this);
    }
}

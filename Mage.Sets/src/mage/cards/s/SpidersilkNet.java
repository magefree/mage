package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author North
 */
public final class SpidersilkNet extends CardImpl {

    public SpidersilkNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");
        this.subtype.add(SubType.EQUIPMENT);

        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(0, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has reach"));
        this.addAbility(ability);

        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    private SpidersilkNet(final SpidersilkNet card) {
        super(card);
    }

    @Override
    public SpidersilkNet copy() {
        return new SpidersilkNet(this);
    }
}

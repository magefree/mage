package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;


/**
 * @author emerald000
 */
public final class ShieldOfTheAvatar extends CardImpl {

    public ShieldOfTheAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(new PreventDamageToAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, false, CreaturesYouControlCount.PLURAL)
                .setText("If a source would deal damage to equipped creature, prevent X of that damage, where X is the number of creatures you control."))
                .addHint(CreaturesYouControlHint.instance));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private ShieldOfTheAvatar(final ShieldOfTheAvatar card) {
        super(card);
    }

    @Override
    public ShieldOfTheAvatar copy() {
        return new ShieldOfTheAvatar(this);
    }
}

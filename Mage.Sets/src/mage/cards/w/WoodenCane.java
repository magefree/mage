package mage.cards.w;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.RedMutantToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WoodenCane extends CardImpl {

    public WoodenCane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 2/2 red Mutant creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new RedMutantToken())));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private WoodenCane(final WoodenCane card) {
        super(card);
    }

    @Override
    public WoodenCane copy() {
        return new WoodenCane(this);
    }
}

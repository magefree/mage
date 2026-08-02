package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.Dwarf22Token;
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
public final class DwarvenShortsword extends CardImpl {

    public DwarvenShortsword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 2/2 red Dwarf creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new Dwarf22Token())));

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private DwarvenShortsword(final DwarvenShortsword card) {
        super(card);
    }

    @Override
    public DwarvenShortsword copy() {
        return new DwarvenShortsword(this);
    }
}

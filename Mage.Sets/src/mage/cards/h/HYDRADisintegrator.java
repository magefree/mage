package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.VillainToken;

/**
 *
 * @author muz
 */
public final class HYDRADisintegrator extends CardImpl {

    public HYDRADisintegrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 2/1 black Villain creature token with menace, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new CreateTokenAttachSourceEffect(new VillainToken())
        ));

        // Equipped creature gets +3/+3.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 3)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private HYDRADisintegrator(final HYDRADisintegrator card) {
        super(card);
    }

    @Override
    public HYDRADisintegrator copy() {
        return new HYDRADisintegrator(this);
    }
}

package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyoshiBattleFan extends CardImpl {

    public KyoshiBattleFan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 1/1 white Ally creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new AllyToken())));

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private KyoshiBattleFan(final KyoshiBattleFan card) {
        super(card);
    }

    @Override
    public KyoshiBattleFan copy() {
        return new KyoshiBattleFan(this);
    }
}

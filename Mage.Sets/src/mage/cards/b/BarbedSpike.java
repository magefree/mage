package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BarbedSpike extends CardImpl {

    public BarbedSpike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Barbed Spike enters the battlefield, create a 1/1 colorless Thopter artifact creature token with flying and attach Barbed Spike to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenAttachSourceEffect(new ThopterColorlessToken())
        ));

        // Equipped creature gets +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 0)));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private BarbedSpike(final BarbedSpike card) {
        super(card);
    }

    @Override
    public BarbedSpike copy() {
        return new BarbedSpike(this);
    }
}

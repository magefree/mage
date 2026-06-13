package mage.cards.m;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MidnightAngelArmor extends CardImpl {

    public MidnightAngelArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 1/1 white Soldier creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new SoldierToken())));

        // Equipped creature gets +3/+3 and has flying and vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(
            new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has flying"));
        ability.addEffect(
            new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and vigilance"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private MidnightAngelArmor(final MidnightAngelArmor card) {
        super(card);
    }

    @Override
    public MidnightAngelArmor copy() {
        return new MidnightAngelArmor(this);
    }
}

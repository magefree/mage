package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuxiliaryBoosters extends CardImpl {

    public AuxiliaryBoosters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 2/2 colorless Robot artifact creature token and attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new RobotToken(), " and")));

        // Equipped creature gets +1/+2 and has flying.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has flying"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private AuxiliaryBoosters(final AuxiliaryBoosters card) {
        super(card);
    }

    @Override
    public AuxiliaryBoosters copy() {
        return new AuxiliaryBoosters(this);
    }
}

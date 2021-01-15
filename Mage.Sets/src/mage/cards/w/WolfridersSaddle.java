package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolfridersSaddle extends CardImpl {

    public WolfridersSaddle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Wolfrider's Saddle enters the battlefield, create a 2/2 green Wolf creature token, then attach Wolfrider's Saddle to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new WolfToken())));

        // Equipped creature gets +1/+1 and can't be blocked by more than one creature.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new SimpleStaticAbility(new CantBeBlockedByMoreThanOneSourceEffect()), AttachmentType.EQUIPMENT
        ).setText("and can't be blocked by more than one creature"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private WolfridersSaddle(final WolfridersSaddle card) {
        super(card);
    }

    @Override
    public WolfridersSaddle copy() {
        return new WolfridersSaddle(this);
    }
}

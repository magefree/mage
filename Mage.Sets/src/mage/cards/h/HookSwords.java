package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HookSwords extends CardImpl {

    public HookSwords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R/W}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 1/1 white Ally creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new AllyToken())));

        // During your turn, equipped creature gets +1/+1 and has first strike.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(1, 1), MyTurnCondition.instance,
                "during your turn, equipped creature gets +1/+1"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "and has first strike"
        ));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private HookSwords(final HookSwords card) {
        super(card);
    }

    @Override
    public HookSwords copy() {
        return new HookSwords(this);
    }
}

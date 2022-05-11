package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CitizenGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScepterOfCelebration extends CardImpl {

    public ScepterOfCelebration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, create that many 1/1 green and white Citizen creature tokens.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new CreateTokenEffect(
                new CitizenGreenWhiteToken(), SavedDamageValue.MANY
        ), "equipped", false));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private ScepterOfCelebration(final ScepterOfCelebration card) {
        super(card);
    }

    @Override
    public ScepterOfCelebration copy() {
        return new ScepterOfCelebration(this);
    }
}

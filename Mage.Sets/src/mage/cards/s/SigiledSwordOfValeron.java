package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SigiledSwordOfValeron extends CardImpl {

    public SigiledSwordOfValeron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0, has vigilance, and is a Knight in addition to its other types.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEquippedEffect(2, 0)
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(),
                AttachmentType.EQUIPMENT
        ).setText(", has vigilance"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.KNIGHT,
                Duration.WhileOnBattlefield,
                AttachmentType.EQUIPMENT
        ).setText(", and is a Knight in addition to its other types"));
        this.addAbility(ability);

        // Whenever equipped creature attacks, create a 2/2 white Knight creature token with vigilance that's attacking.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new CreateTokenEffect(new KnightToken(), 1, false, true)
        ));

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), new TargetControlledCreaturePermanent(), false));
    }

    private SigiledSwordOfValeron(final SigiledSwordOfValeron card) {
        super(card);
    }

    @Override
    public SigiledSwordOfValeron copy() {
        return new SigiledSwordOfValeron(this);
    }
}

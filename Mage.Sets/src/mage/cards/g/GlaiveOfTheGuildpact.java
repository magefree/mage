package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class GlaiveOfTheGuildpact extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("Gate you control");

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public GlaiveOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each Gate you control and has vigilance and menace.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEquippedEffect(
                        new PermanentsOnBattlefieldCount(filter),
                        new StaticValue(0)
                )
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and menace"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    public GlaiveOfTheGuildpact(final GlaiveOfTheGuildpact card) {
        super(card);
    }

    @Override
    public GlaiveOfTheGuildpact copy() {
        return new GlaiveOfTheGuildpact(this);
    }
}

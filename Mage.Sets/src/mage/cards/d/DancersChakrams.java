package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.*;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.l.Lifelink;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

/**
 * @author balazskristof
 */
public final class DancersChakrams extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("commanders you control");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent("commanders you control");

    static {
        filter.add(CommanderPredicate.instance);
        filter2.add(CommanderPredicate.instance);
    }

    public DancersChakrams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{W}");
        
        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +2/+2, has lifelink and "Other commanders you control get +2/+2 and have lifelink," and is a Performer in addition to its other types.
        Ability commanderAbility = new SimpleStaticAbility(new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, true));
        commanderAbility.addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter2)
                .setText("and have lifelink"));
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText(", has lifelink"));
        ability.addEffect(new GainAbilityAttachedEffect(commanderAbility, AttachmentType.EQUIPMENT)
                .setText("and \"Other commanders you control get +2/+2 and have lifelink,\""));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.PERFORMER, AttachmentType.EQUIPMENT)
                .setText("and is a Performer in addition to its other types"));
        this.addAbility(ability);

        // Krishna -- Equip {3}
        this.addAbility(new EquipAbility(3).withFlavorWord("Krishna"));
    }

    private DancersChakrams(final DancersChakrams card) {
        super(card);
    }

    @Override
    public DancersChakrams copy() {
        return new DancersChakrams(this);
    }
}

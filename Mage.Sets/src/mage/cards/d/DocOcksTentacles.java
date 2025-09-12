package mage.cards.d;

import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DocOcksTentacles extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 4));
    }

    public DocOcksTentacles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever a creature you control with mana value 5 or greater enters, you may attach this Equipment to it.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AttachEffect(Outcome.BoostCreature, "attach {this} to it"), filter, true
        ));

        // Equipped creature gets +4/+4.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(4, 4)));

        // Equip {5}
        this.addAbility(new EquipAbility(5));
    }

    private DocOcksTentacles(final DocOcksTentacles card) {
        super(card);
    }

    @Override
    public DocOcksTentacles copy() {
        return new DocOcksTentacles(this);
    }
}

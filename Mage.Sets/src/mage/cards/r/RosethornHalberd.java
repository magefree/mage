package mage.cards.r;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RosethornHalberd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("non-Human creature you control");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public RosethornHalberd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Rosethorn Halberd enters the battlefield, attach it to target non-Human creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget(filter));

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {5}
        this.addAbility(new EquipAbility(5));
    }

    private RosethornHalberd(final RosethornHalberd card) {
        super(card);
    }

    @Override
    public RosethornHalberd copy() {
        return new RosethornHalberd(this);
    }
}

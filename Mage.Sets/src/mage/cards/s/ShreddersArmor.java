package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ShreddersArmor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another nonland permanent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public ShreddersArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // When this Equipment enters, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equip--Sacrifice another nonland permanent. Activate only once each turn.
        EquipAbility equipAbility = new EquipAbility(Outcome.BoostCreature, new SacrificeTargetCost(filter), false);
        equipAbility.setMaxActivationsPerTurn(1);
        this.addAbility(equipAbility);
    }

    private ShreddersArmor(final ShreddersArmor card) {
        super(card);
    }

    @Override
    public ShreddersArmor copy() {
        return new ShreddersArmor(this);
    }
}

package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsAttachedAttackingPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class HeartPiercerBow extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsAttachedAttackingPredicate.instance);
    }

    public HeartPiercerBow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, Heart-Piercer Bow deals 1 damage to target creature defending player controls.
        Ability ability = new AttacksAttachedTriggeredAbility(new DamageTargetEffect(1));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.Benefit, new GenericManaCost(1)));
    }

    private HeartPiercerBow(final HeartPiercerBow card) {
        super(card);
    }

    @Override
    public HeartPiercerBow copy() {
        return new HeartPiercerBow(this);
    }
}

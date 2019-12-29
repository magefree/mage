package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkeletonArcher extends CardImpl {

    public SkeletonArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Skeleton Archer enters the battlefield, it deals 1 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"), false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public SkeletonArcher(final SkeletonArcher card) {
        super(card);
    }

    @Override
    public SkeletonArcher copy() {
        return new SkeletonArcher(this);
    }
}

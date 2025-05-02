package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWhileSaddledTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DracosaurAuxiliary extends CardImpl {

    public DracosaurAuxiliary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever this creature attacks while saddled, it deals 2 damage to any target.
        Ability ability = new AttacksWhileSaddledTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Saddle 3
        this.addAbility(new SaddleAbility(3));
    }

    private DracosaurAuxiliary(final DracosaurAuxiliary card) {
        super(card);
    }

    @Override
    public DracosaurAuxiliary copy() {
        return new DracosaurAuxiliary(this);
    }
}

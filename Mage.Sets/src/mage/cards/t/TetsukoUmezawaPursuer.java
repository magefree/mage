package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.BlocksAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;

import java.util.UUID;

/**
 *
 * @author miesma
 */
public final class TetsukoUmezawaPursuer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with power or toughness 1 or less");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.or(
                new PowerPredicate(ComparisonType.OR_LESS, 1),
                new ToughnessPredicate(ComparisonType.OR_LESS, 1)
        ));
    }

    public TetsukoUmezawaPursuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever a creature an opponent controls with power or toughness 1 or less blocks, Tetsuko Umezawa deals 1 damage to that creature’s controller.
        this.addAbility(new BlocksAllTriggeredAbility(
                new DamageTargetEffect(1).withTargetDescription("that creature's controller"),
                false, filter, SetTargetPointer.PLAYER
        ));
    }

    private TetsukoUmezawaPursuer(final TetsukoUmezawaPursuer card) {
        super(card);
    }

    @Override
    public TetsukoUmezawaPursuer copy() {
        return new TetsukoUmezawaPursuer(this);
    }
}

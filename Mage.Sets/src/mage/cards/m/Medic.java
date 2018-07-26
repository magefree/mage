package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author NinthWorld
 */
public final class Medic extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other nonartifact creatures you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));
    }

    public Medic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Other nonartifact creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public Medic(final Medic card) {
        super(card);
    }

    @Override
    public Medic copy() {
        return new Medic(this);
    }
}



package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */


public final class PontiffOfBlight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other creatures you control");
    static {
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PontiffOfBlight (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Extort
        this.addAbility(new ExtortAbility());

        // Other creatures you control have extort. (If a creature has multiple instances of extort, each triggers separately.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new ExtortAbility(), Duration.WhileOnBattlefield, filter)));

    }

    public PontiffOfBlight (final PontiffOfBlight card) {
        super(card);
    }

    @Override
    public PontiffOfBlight copy() {
        return new PontiffOfBlight(this);
    }

}


package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class MurasaPyromancer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Ally you control");

    static {
        filter.add(new SubtypePredicate(SubType.ALLY));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public MurasaPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new DamageTargetEffect(new PermanentsOnBattlefieldCount(filter)), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    public MurasaPyromancer(final MurasaPyromancer card) {
        super(card);
    }

    @Override
    public MurasaPyromancer copy() {
        return new MurasaPyromancer(this);
    }
}


package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KapshoKitefins extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("{this} or another creature");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterTarget.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public KapshoKitefins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.FISH);

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Kapsho Kitefins or another creature enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new TapTargetEffect(), filter,  false);
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        this.addAbility(ability);

    }

    public KapshoKitefins(final KapshoKitefins card) {
        super(card);
    }

    @Override
    public KapshoKitefins copy() {
        return new KapshoKitefins(this);
    }
}

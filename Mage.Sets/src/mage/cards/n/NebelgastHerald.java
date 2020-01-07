
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NebelgastHerald extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("{this} or another Spirit");
    private static final FilterCreaturePermanent filterTarget = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.SPIRIT.getPredicate());
        filterTarget.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public NebelgastHerald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Nebelgast Herald or another Spirit enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new TapTargetEffect(), filter, false);
        ability.addTarget(new TargetCreaturePermanent(filterTarget));
        this.addAbility(ability);
    }

    public NebelgastHerald(final NebelgastHerald card) {
        super(card);
    }

    @Override
    public NebelgastHerald copy() {
        return new NebelgastHerald(this);
    }
}

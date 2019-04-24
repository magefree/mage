
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OrcSureshot extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature under your control");
    private static final FilterCreaturePermanent filterOpponentCreature = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new AnotherPredicate());
        filterOpponentCreature.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public OrcSureshot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostTargetEffect(-1,-1, Duration.EndOfTurn),filter,false);
        ability.addTarget(new TargetCreaturePermanent(filterOpponentCreature));
        this.addAbility(ability);
        
    }

    public OrcSureshot(final OrcSureshot card) {
        super(card);
    }

    @Override
    public OrcSureshot copy() {
        return new OrcSureshot(this);
    }
}

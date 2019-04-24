
package mage.cards.u;

import java.util.EnumSet;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class UnstoppableAsh extends CardImpl {

    final static private FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public UnstoppableAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Champion a Treefolk or Warrior
        this.addAbility(new ChampionAbility(this, EnumSet.of(SubType.TREEFOLK, SubType.WARRIOR), false));
        
        // Whenever a creature you control becomes blocked, it gets +0/+5 until end of turn.
        Effect effect = new BoostTargetEffect(0, 5, Duration.EndOfTurn);
        effect.setText("it gets +0/+5 until end of turn");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, true));

    }

    public UnstoppableAsh(final UnstoppableAsh card) {
        super(card);
    }

    @Override
    public UnstoppableAsh copy() {
        return new UnstoppableAsh(this);
    }
}

package mage.cards.u;

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

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class UnstoppableAsh extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public UnstoppableAsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Champion a Treefolk or Warrior
        this.addAbility(new ChampionAbility(this, SubType.TREEFOLK, SubType.WARRIOR));

        // Whenever a creature you control becomes blocked, it gets +0/+5 until end of turn.
        Effect effect = new BoostTargetEffect(0, 5, Duration.EndOfTurn);
        effect.setText("it gets +0/+5 until end of turn");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, true));
    }

    private UnstoppableAsh(final UnstoppableAsh card) {
        super(card);
    }

    @Override
    public UnstoppableAsh copy() {
        return new UnstoppableAsh(this);
    }
}

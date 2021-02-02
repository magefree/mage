
package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class BattleRattleShaman extends CardImpl {

    public BattleRattleShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may have target creature get +2/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD,
            new BoostTargetEffect(2, 0, Duration.EndOfTurn).setText("you may have target creature get +2/+0 until end of turn"),
            TargetController.YOU, true, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private BattleRattleShaman(final BattleRattleShaman card) {
        super(card);
    }

    @Override
    public BattleRattleShaman copy() {
        return new BattleRattleShaman(this);
    }
}

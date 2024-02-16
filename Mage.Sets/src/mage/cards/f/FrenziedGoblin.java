
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FrenziedGoblin extends CardImpl {

    public FrenziedGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Frenzied Goblin attacks, you may pay {R}. If you do, target creature can't block this turn.
        Ability ability = new AttacksTriggeredAbility(new DoIfCostPaid(new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{R}")),false,
                "Whenever {this} attacks, you may pay {R}. If you do, target creature can't block this turn.");
        Target target = new TargetCreaturePermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private FrenziedGoblin(final FrenziedGoblin card) {
        super(card);
    }

    @Override
    public FrenziedGoblin copy() {
        return new FrenziedGoblin(this);
    }
}

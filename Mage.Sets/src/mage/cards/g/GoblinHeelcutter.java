
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GoblinHeelcutter extends CardImpl {

    public GoblinHeelcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Goblin Heelcutter attacks, target creature can't block this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Dash {2}{R} (You may cast this spell for its dash cost. If you do, it gains haste, and it's returned from the battlefield to its owner's hand at the beginning of the next end step.)
        this.addAbility(new DashAbility("{2}{R}"));

    }

    private GoblinHeelcutter(final GoblinHeelcutter card) {
        super(card);
    }

    @Override
    public GoblinHeelcutter copy() {
        return new GoblinHeelcutter(this);
    }
}

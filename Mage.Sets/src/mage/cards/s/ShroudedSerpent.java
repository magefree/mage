
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class ShroudedSerpent extends CardImpl {

    public ShroudedSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        
        // Whenever Shrouded Serpent attacks, defending player may pay {4}. If they don't, Shrouded Serpent can't be blocked this turn.
        this.addAbility(new AttacksTriggeredAbility(
                new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new CantBeBlockedByAllSourceEffect(new FilterCreaturePermanent(), Duration.EndOfCombat), new ManaCostsImpl<>("{4}")),
                false, 
                "Whenever {this} attacks, defending player may pay {4}. If they don't, {this} can't be blocked this turn.", 
                SetTargetPointer.PLAYER));
    }

    private ShroudedSerpent(final ShroudedSerpent card) {
        super(card);
    }

    @Override
    public ShroudedSerpent copy() {
        return new ShroudedSerpent(this);
    }
}


package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class GoblinVandal extends CardImpl {

    public GoblinVandal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Vandal attacks and isn't blocked, you may pay {R}. If you do, destroy target artifact defending player controls and Goblin Vandal assigns no combat damage this turn.
        DoIfCostPaid effect = new DoIfCostPaid(new DestroyTargetEffect().setText("destroy target artifact defending player controls"),
                new ManaCostsImpl<>("{R}"), "Pay {R} to destroy artifact of defending player?");
        effect.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn).setText("and {this} assigns no combat damage this turn"));
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, false, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetArtifactPermanent());
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private GoblinVandal(final GoblinVandal card) {
        super(card);
    }

    @Override
    public GoblinVandal copy() {
        return new GoblinVandal(this);
    }
}

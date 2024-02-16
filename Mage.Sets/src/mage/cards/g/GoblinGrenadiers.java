package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author LoneFox
 */
public final class GoblinGrenadiers extends CardImpl {

    public GoblinGrenadiers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Goblin Grenadiers attacks and isn't blocked, you may sacrifice it. If you do, destroy target creature and target land.
        Effect effect = new DoIfCostPaid(new DestroyTargetEffect(), new SacrificeSourceCost(), "Sacrifice {this} to destroy target creature and target land?");
        effect.setTargetPointer(new EachTargetPointer());
        effect.setText("you may sacrifice it. If you do, destroy target creature and target land");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private GoblinGrenadiers(final GoblinGrenadiers card) {
        super(card);
    }

    @Override
    public GoblinGrenadiers copy() {
        return new GoblinGrenadiers(this);
    }
}

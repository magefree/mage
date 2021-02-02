
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class TroubledHealer extends CardImpl {

    public TroubledHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice a land: Prevent the next 2 damage that would be dealt to any target this turn.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD, 
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 2),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("land"))));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TroubledHealer(final TroubledHealer card) {
        super(card);
    }

    @Override
    public TroubledHealer copy() {
        return new TroubledHealer(this);
    }
}

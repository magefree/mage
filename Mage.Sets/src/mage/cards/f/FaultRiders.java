
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class FaultRiders extends CardImpl {

    public FaultRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sacrifice a land: Fault Riders gets +2/+0 and gains first strike until end of turn. Activate this ability only once each turn.
        Effect effect = new BoostSourceEffect(2,0, Duration.EndOfTurn);
        effect.setText("{this} gets +2/+0");
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, 
                effect, 
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        effect = new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains first strike until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FaultRiders(final FaultRiders card) {
        super(card);
    }

    @Override
    public FaultRiders copy() {
        return new FaultRiders(this);
    }
}

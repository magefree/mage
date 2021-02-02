
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class FurystokeGiant extends CardImpl {

    public FurystokeGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Furystoke Giant enters the battlefield, other creatures you control gain "{tap}: This creature deals 2 damage to any target" until end of turn.
        SimpleActivatedAbility FurystokeGiantAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        FurystokeGiantAbility.addTarget(new TargetAnyTarget());
        Effect effect = new GainAbilityAllEffect(FurystokeGiantAbility, Duration.EndOfTurn, new FilterControlledCreaturePermanent("other creatures"), true);
        effect.setText("other creatures you control gain \"{T}: This creature deals 2 damage to any target\" until end of turn.");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
        
        // Persist
        this.addAbility(new PersistAbility());
        
    }

    private FurystokeGiant(final FurystokeGiant card) {
        super(card);
    }

    @Override
    public FurystokeGiant copy() {
        return new FurystokeGiant(this);
    }
}

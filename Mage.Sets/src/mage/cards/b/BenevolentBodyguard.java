
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class BenevolentBodyguard extends CardImpl {

    public BenevolentBodyguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Benevolent Bodyguard: Target creature you control gains protection from the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), new SacrificeSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BenevolentBodyguard(final BenevolentBodyguard card) {
        super(card);
    }

    @Override
    public BenevolentBodyguard copy() {
        return new BenevolentBodyguard(this);
    }
}

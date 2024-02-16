
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class GhostLitNourisher extends CardImpl {

    public GhostLitNourisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}: Target creature gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        // Channel - {3}{G}, Discard Ghost-Lit Nourisher: Target creature gets +4/+4 until end of turn.
        Ability ability2 = new ChannelAbility("{3}{G}", new BoostTargetEffect(4, 4, Duration.EndOfTurn));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private GhostLitNourisher(final GhostLitNourisher card) {
        super(card);
    }

    @Override
    public GhostLitNourisher copy() {
        return new GhostLitNourisher(this);
    }
}

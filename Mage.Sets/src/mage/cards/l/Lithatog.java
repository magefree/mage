
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class Lithatog extends CardImpl {

    public Lithatog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.ATOG);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Sacrifice an artifact: Lithatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledArtifactPermanent("an artifact")))));
        // Sacrifice a land: Lithatog gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1,1, Duration.EndOfTurn),
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))));
    }

    private Lithatog(final Lithatog card) {
        super(card);
    }

    @Override
    public Lithatog copy() {
        return new Lithatog(this);
    }
}

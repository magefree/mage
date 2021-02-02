
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author daagar
 */
public final class MyrLandshaper extends CardImpl {

    public MyrLandshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.MYR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target land becomes an artifact in addition to its other types until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCardTypeTargetEffect(Duration.EndOfTurn, CardType.ARTIFACT), new TapSourceCost());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private MyrLandshaper(final MyrLandshaper card) {
        super(card);
    }

    @Override
    public MyrLandshaper copy() {
        return new MyrLandshaper(this);
    }
}


package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class NemataGroveGuardian extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Saproling creatures");
    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("Saproling");

    static {
        filter.add(SubType.SAPROLING.getPredicate());
        filter1.add(SubType.SAPROLING.getPredicate());
    }

    public NemataGroveGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // {2}{G}: Create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SaprolingToken()), new ManaCostsImpl<>("{2}{G}")));
        // Sacrifice a Saproling: Saproling creatures get +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false), new SacrificeTargetCost(new TargetControlledPermanent(filter1))));
    }

    private NemataGroveGuardian(final NemataGroveGuardian card) {
        super(card);
    }

    @Override
    public NemataGroveGuardian copy() {
        return new NemataGroveGuardian(this);
    }
}

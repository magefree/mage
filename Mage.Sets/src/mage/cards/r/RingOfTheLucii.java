package mage.cards.r;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RingOfTheLucii extends CardImpl {

    public RingOfTheLucii(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));

        // {2}, {T}, Pay 1 life: Tap target nonland permanent.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayLifeCost(1));
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private RingOfTheLucii(final RingOfTheLucii card) {
        super(card);
    }

    @Override
    public RingOfTheLucii copy() {
        return new RingOfTheLucii(this);
    }
}

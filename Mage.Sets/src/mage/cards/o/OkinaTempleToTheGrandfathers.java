

package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class OkinaTempleToTheGrandfathers extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public OkinaTempleToTheGrandfathers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.supertype.add(SuperType.LEGENDARY);
        this.addAbility(new GreenManaAbility());
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OkinaTempleToTheGrandfathers(final OkinaTempleToTheGrandfathers card) {
        super(card);
    }

    @Override
    public OkinaTempleToTheGrandfathers copy() {
        return new OkinaTempleToTheGrandfathers(this);
    }

}

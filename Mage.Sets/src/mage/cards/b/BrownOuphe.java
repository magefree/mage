
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ArtifactSourcePredicate;
import mage.target.common.TargetActivatedAbility;

/**
 *
 * @author TheElk801
 */
public final class BrownOuphe extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("activated ability from an artifact source");

    static {
        filter.add(ArtifactSourcePredicate.instance);
    }

    public BrownOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}, {tap}: Counter target activated ability from an artifact source.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetActivatedAbility(filter));
        this.addAbility(ability);
    }

    private BrownOuphe(final BrownOuphe card) {
        super(card);
    }

    @Override
    public BrownOuphe copy() {
        return new BrownOuphe(this);
    }
}

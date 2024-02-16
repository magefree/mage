
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class VizierOfTumblingSands extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VizierOfTumblingSands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Untap another target permanent.
        Effect effect = new UntapTargetEffect();
        effect.setText("Untap another target permanent");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Cycling {1}{U}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{U}")));

        // When you cycle Vizier of Tumbling Sands, untap target permanent.
        ability = new CycleTriggeredAbility(new UntapTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private VizierOfTumblingSands(final VizierOfTumblingSands card) {
        super(card);
    }

    @Override
    public VizierOfTumblingSands copy() {
        return new VizierOfTumblingSands(this);
    }
}

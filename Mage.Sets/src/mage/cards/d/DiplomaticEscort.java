
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.TargetStackObject;

/**
 *
 * @author LoneFox
 */
public final class DiplomaticEscort extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spell or ability that targets a creature");

    static {
        filter.add(new TargetsPermanentPredicate(new FilterCreaturePermanent()));
    }

    public DiplomaticEscort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}, {tap}, Discard a card: Counter target spell or ability that targets a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetStackObject(filter));
        this.addAbility(ability);
    }

    private DiplomaticEscort(final DiplomaticEscort card) {
        super(card);
    }

    @Override
    public DiplomaticEscort copy() {
        return new DiplomaticEscort(this);
    }
}

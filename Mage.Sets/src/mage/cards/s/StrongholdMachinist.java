
package mage.cards.s;

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
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author anonymous
 */
public final class StrongholdMachinist extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("noncreature spell");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public StrongholdMachinist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPELLSHAPER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U}{U}, {tap}, Discard a card: Counter target noncreature spell.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterTargetEffect(), new ManaCostsImpl("{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);
    }

    public StrongholdMachinist(final StrongholdMachinist card) {
        super(card);
    }

    @Override
    public StrongholdMachinist copy() {
        return new StrongholdMachinist(this);
    }
}

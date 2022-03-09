
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class AzcantaTheSunkenRuin extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public AzcantaTheSunkenRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // (Transforms from Search for Azcanta)/
        // {T} : Add {U}.
        this.addAbility(new BlueManaAbility());

        // {2}{U} , {T} : Look at the top four cards of your library. You may reveal a noncreature, nonland card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(4), false, StaticValue.get(1),
                        filter, Zone.LIBRARY, false, true, true
                ), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AzcantaTheSunkenRuin(final AzcantaTheSunkenRuin card) {
        super(card);
    }

    @Override
    public AzcantaTheSunkenRuin copy() {
        return new AzcantaTheSunkenRuin(this);
    }
}

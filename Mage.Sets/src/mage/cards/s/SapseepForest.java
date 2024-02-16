
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author jeffwadsworth
 */
public final class SapseepForest extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("if you control two or more green permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SapseepForest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.FOREST);

        // <i>({tap}: Add {G}.)</i>
        this.addAbility(new GreenManaAbility());

        // Sapseep Forest enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {G}, {tap}: You gain 1 life. Activate this ability only if you control two or more green permanents.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new GainLifeEffect(1),
                new ManaCostsImpl<>("{G}"),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private SapseepForest(final SapseepForest card) {
        super(card);
    }

    @Override
    public SapseepForest copy() {
        return new SapseepForest(this);
    }
}


package mage.cards.m;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.mana.RedManaAbility;
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
public final class MadblindMountain extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("if you control two or more red permanents");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public MadblindMountain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.MOUNTAIN);

        // <i>({tap}: Add {R}.)</i>
        this.addAbility(new RedManaAbility());

        // Madblind Mountain enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {R}, {tap}: Shuffle your library. Activate this ability only if you control two or more red permanents.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new ShuffleLibrarySourceEffect(),
                new ManaCostsImpl<>("{R}"),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private MadblindMountain(final MadblindMountain card) {
        super(card);
    }

    @Override
    public MadblindMountain copy() {
        return new MadblindMountain(this);
    }
}

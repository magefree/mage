
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.AddCardTypeTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Addictiveme
 */
public final class MechHangar extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Pilot or Vehicle spell");

    static {
        filter.add(Predicates.or(SubType.VEHICLE.getPredicate(), SubType.PILOT.getPredicate()));
    }

    public MechHangar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Pilot or Vehicle spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(filter), true));

        // {3}, {T}, Target Vehicle becomes an artifact creature until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCardTypeTargetEffect(
        		Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE)
        		.setText("Target Vehicle becomes an artifact creature until end of turn"), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(new FilterPermanent(SubType.VEHICLE, "Vehicle")));
        this.addAbility(ability);
    }

    private MechHangar(final MechHangar card) {
        super(card);
    }

    @Override
    public MechHangar copy() {
        return new MechHangar(this);
    }
}

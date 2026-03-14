package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class TurtleLair extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Turtle or Ninja spell");
    private static final FilterPermanent filter2 = new FilterPermanent("Ninja or Turtle");

    static {
        filter.add(Predicates.or(SubType.TURTLE.getPredicate(), SubType.NINJA.getPredicate()));
        filter2.add(Predicates.or(SubType.TURTLE.getPredicate(), SubType.NINJA.getPredicate()));
    }

    public TurtleLair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Ninja or Turtle spell.
        this.addAbility(new ConditionalAnyColorManaAbility(new TapSourceCost(), 1, new ConditionalSpellManaBuilder(filter), true));

        // {3}, {T}: Target Ninja or Turtle can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private TurtleLair(final TurtleLair card) {
        super(card);
    }

    @Override
    public TurtleLair copy() {
        return new TurtleLair(this);
    }
}

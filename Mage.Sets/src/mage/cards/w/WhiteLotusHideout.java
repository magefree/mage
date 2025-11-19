package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WhiteLotusHideout extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Lesson or Shrine spell");

    static {
        filter.add(Predicates.or(
                SubType.LESSON.getPredicate(),
                SubType.SHRINE.getPredicate()
        ));
    }

    public WhiteLotusHideout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Lesson or Shrine spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new ConditionalSpellManaBuilder(filter)));

        // {1}, {T}: Add one mana of any color.
        Ability ability = new AnyColorManaAbility(new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private WhiteLotusHideout(final WhiteLotusHideout card) {
        super(card);
    }

    @Override
    public WhiteLotusHideout copy() {
        return new WhiteLotusHideout(this);
    }
}

package mage.cards.t;

import mage.Mana;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
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
public final class TournamentGrounds extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Knight or Equipment spell");

    static {
        filter.add(Predicates.or(
                SubType.KNIGHT.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public TournamentGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add {R}, {W}, or {B}. Spend this mana only to cast a Knight or Equipment spell.
        this.addAbility(new ConditionalColoredManaAbility(Mana.RedMana(1), new ConditionalSpellManaBuilder(filter)));
        this.addAbility(new ConditionalColoredManaAbility(Mana.WhiteMana(1), new ConditionalSpellManaBuilder(filter)));
        this.addAbility(new ConditionalColoredManaAbility(Mana.BlackMana(1), new ConditionalSpellManaBuilder(filter)));
    }

    private TournamentGrounds(final TournamentGrounds card) {
        super(card);
    }

    @Override
    public TournamentGrounds copy() {
        return new TournamentGrounds(this);
    }
}

package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.ConditionalAnyColorManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaelstromOfTheSpiritDragon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Dragon spell or an Omen spell");
    private static final FilterCard filter2 = new FilterCard("a Dragon card");

    static {
        filter.add(Predicates.or(
                SubType.DRAGON.getPredicate(),
                SubType.OMEN.getPredicate()
        ));
        filter2.add(SubType.DRAGON.getPredicate());
    }

    public MaelstromOfTheSpiritDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}: Add one mana of any color. Spend this mana only to cast a Dragon spell or an Omen spell.
        this.addAbility(new ConditionalAnyColorManaAbility(1, new ConditionalSpellManaBuilder(filter)));

        // {4}, {T}, Sacrifice this land: Search your library for a Dragon card, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter2), true), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MaelstromOfTheSpiritDragon(final MaelstromOfTheSpiritDragon card) {
        super(card);
    }

    @Override
    public MaelstromOfTheSpiritDragon copy() {
        return new MaelstromOfTheSpiritDragon(this);
    }
}

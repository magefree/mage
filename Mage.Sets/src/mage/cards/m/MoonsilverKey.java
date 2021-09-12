package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.ManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonsilverKey extends CardImpl {

    private static final FilterCard filter
            = new FilterCard("an artifact card with a mana ability or a basic land card");

    static {
        filter.add(MoonsilverKeyPredicate.instance);
    }

    public MoonsilverKey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {T}, Sacrifice Moonsilver Key: Search your library for an artifact card with a mana ability or a basic land card, reveal it, put it into your hand, then shuffle.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        ), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MoonsilverKey(final MoonsilverKey card) {
        super(card);
    }

    @Override
    public MoonsilverKey copy() {
        return new MoonsilverKey(this);
    }
}

enum MoonsilverKeyPredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        if (input.isLand(game) && input.isBasic()) {
            return true;
        }
        return input.isArtifact(game)
                && input
                .getAbilities(game)
                .stream()
                .anyMatch(ManaAbility.class::isInstance);
    }
}
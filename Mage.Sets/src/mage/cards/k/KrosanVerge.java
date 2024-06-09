package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrosanVerge extends CardImpl {

    public KrosanVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Krosan Verge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice Krosan Verge: Search your library for a Forest card and a Plains card and put them onto the battlefield tapped. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInPlayEffect(
                new KrosanVergeTarget(), true
        ).setText("search your library for a Forest card and a Plains card, put them onto the battlefield tapped, then shuffle"), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KrosanVerge(final KrosanVerge card) {
        super(card);
    }

    @Override
    public KrosanVerge copy() {
        return new KrosanVerge(this);
    }
}

class KrosanVergeTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a Forest card and a Plains card");

    static {
        filter.add(Predicates.or(
                SubType.FOREST.getPredicate(),
                SubType.PLAINS.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner
            = new SubTypeAssignment(SubType.FOREST, SubType.PLAINS);

    KrosanVergeTarget() {
        super(0, 2, filter);
    }

    private KrosanVergeTarget(final KrosanVergeTarget target) {
        super(target);
    }

    @Override
    public KrosanVergeTarget copy() {
        return new KrosanVergeTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}

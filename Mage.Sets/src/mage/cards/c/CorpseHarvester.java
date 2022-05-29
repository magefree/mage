package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author jeffwadsworth
 */
public final class CorpseHarvester extends CardImpl {

    public CorpseHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{B}, {tap}, Sacrifice a creature: Search your library for a Zombie card and a Swamp card, reveal them, and put them into your hand. Then shuffle your library.
        Ability ability = new SimpleActivatedAbility(new SearchLibraryPutInHandEffect(
                new CorpseHarvesterTarget(), true
        ), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private CorpseHarvester(final CorpseHarvester card) {
        super(card);
    }

    @Override
    public CorpseHarvester copy() {
        return new CorpseHarvester(this);
    }
}

class CorpseHarvesterTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a Zombie card and a Swamp card");

    static {
        filter.add(Predicates.or(
                SubType.ZOMBIE.getPredicate(),
                SubType.SWAMP.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner
            = new SubTypeAssignment(SubType.ZOMBIE, SubType.SWAMP);

    CorpseHarvesterTarget() {
        super(0, 2, filter);
    }

    private CorpseHarvesterTarget(final CorpseHarvesterTarget target) {
        super(target);
    }

    @Override
    public CorpseHarvesterTarget copy() {
        return new CorpseHarvesterTarget(this);
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

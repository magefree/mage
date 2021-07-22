package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class MyriadLandscape extends CardImpl {

    public MyriadLandscape(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Myriad Landscape enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {tap}, Sacrifice Myriad Landscape: Search your library for up to two basic land cards that share a land type, put them onto the battlefield tapped, then shuffle your library.
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrarySharingLandType(0, 2), true);
        effect.setText("Search your library for up to two basic land cards that share a land type, put them onto the battlefield tapped, then shuffle");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MyriadLandscape(final MyriadLandscape card) {
        super(card);
    }

    @Override
    public MyriadLandscape copy() {
        return new MyriadLandscape(this);
    }
}

class TargetCardInLibrarySharingLandType extends TargetCardInLibrary {

    private static final FilterCard filterBasicLandCard = new FilterCard("basic land card");

    static {
        filterBasicLandCard.add(Predicates.or(
                SubType.getLandTypes()
                        .stream()
                        .map(SubType::getPredicate)
                        .collect(Collectors.toSet())
        ));
        filterBasicLandCard.add(SuperType.BASIC.getPredicate());
    }

    TargetCardInLibrarySharingLandType(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, filterBasicLandCard);
    }

    private TargetCardInLibrarySharingLandType(final TargetCardInLibrarySharingLandType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        if (!super.canTarget(playerId, id, source, cards, game)) {
            return false;
        }
        if (getTargets().isEmpty()) {
            // first target
            return true;
        }
        Card card1 = game.getCard(getTargets().get(0));
        Card card2 = game.getCard(id);
        return card1 != null
                && card2 != null
                && card1
                .getSubtype(game)
                .stream()
                .filter(SubType.getLandTypes()::contains)
                .anyMatch(subType -> card2.hasSubtype(subType, game));
    }

    @Override
    public TargetCardInLibrarySharingLandType copy() {
        return new TargetCardInLibrarySharingLandType(this);
    }
}

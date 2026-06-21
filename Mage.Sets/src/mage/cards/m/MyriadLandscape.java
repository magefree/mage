package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.assignment.common.SubTypeAssignment;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

import java.util.Arrays;
import java.util.Set;
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
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(2));
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

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(SubType.getBasicLands().toArray(new SubType[0]));

    TargetCardInLibrarySharingLandType(int minNumTargets, int maxNumTargets) {
        super(minNumTargets, maxNumTargets, filterBasicLandCard);
    }

    private TargetCardInLibrarySharingLandType(final TargetCardInLibrarySharingLandType target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);

        // only valid roles
        Cards existingTargets = new CardsImpl(this.getTargets());
        possibleTargets.removeIf(id -> {
            Card card = game.getCard(id);
            if (card == null) {
                return true;
            }
            // first target is valid all the time
            if (getTargets().isEmpty()) {
                return false;
            }
            Cards newTargets = existingTargets.copy();
            newTargets.add(card);
            return !subTypeAssigner.hasSharedRoles(newTargets, game);
        });

        return possibleTargets;
    }

    @Override
    public TargetCardInLibrarySharingLandType copy() {
        return new TargetCardInLibrarySharingLandType(this);
    }
}

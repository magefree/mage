package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OswaldFiddlebender extends CardImpl {

    public OswaldFiddlebender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GNOME);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {W}, {T}, Sacrifice an artifact: Search your library for an artifact card with mana value equal to 1 plus the sacrificed artifact's mana value. Put that card onto the battlefield, then shuffle. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new OswaldFiddlebenderEffect(), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
        )));
        this.addAbility(ability.withFlavorWord("Magical Tinkering"));
    }

    private OswaldFiddlebender(final OswaldFiddlebender card) {
        super(card);
    }

    @Override
    public OswaldFiddlebender copy() {
        return new OswaldFiddlebender(this);
    }
}

class OswaldFiddlebenderEffect extends OneShotEffect {

    OswaldFiddlebenderEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for an artifact card with mana value equal to 1 plus the " +
                "sacrificed artifact's mana value. Put that card onto the battlefield, then shuffle";
    }

    private OswaldFiddlebenderEffect(final OswaldFiddlebenderEffect effect) {
        super(effect);
    }

    @Override
    public OswaldFiddlebenderEffect copy() {
        return new OswaldFiddlebenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sacrificed = source
                .getCosts()
                .stream()
                .filter(SacrificeTargetCost.class::isInstance)
                .map(SacrificeTargetCost.class::cast)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .findFirst()
                .orElse(null);
        if (player == null || sacrificed == null) {
            return false;
        }
        FilterCard filterCard = new FilterArtifactCard(
                "artifact card with mana value " + (sacrificed.getManaValue() + 1)
        );
        filterCard.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, sacrificed.getManaValue() + 1));
        TargetCardInLibrary target = new TargetCardInLibrary(filterCard);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        player.shuffleLibrary(source, game);
        return true;
    }
}

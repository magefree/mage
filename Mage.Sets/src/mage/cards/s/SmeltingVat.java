package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SmeltingVat extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("another artifact");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SmeltingVat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {1}, {T}, Sacrifice another artifact: Reveal the top eight cards of your library. Put up to two noncreature artifact cards with total mana value less than or equal to the sacrificed artifact's mana value from among them onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new SimpleActivatedAbility(new SmeltingVatEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability);
    }

    private SmeltingVat(final SmeltingVat card) {
        super(card);
    }

    @Override
    public SmeltingVat copy() {
        return new SmeltingVat(this);
    }
}

class SmeltingVatEffect extends OneShotEffect {

    SmeltingVatEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top eight cards of your library. Put up to two noncreature artifact cards " +
                "with total mana value less than or equal to the sacrificed artifact's mana value " +
                "from among them onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private SmeltingVatEffect(final SmeltingVatEffect effect) {
        super(effect);
    }

    @Override
    public SmeltingVatEffect copy() {
        return new SmeltingVatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 8));
        player.revealCards(source, cards, game);
        TargetCard target = new SmeltingVatTarget(source);
        player.choose(outcome, cards, target, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class SmeltingVatTarget extends TargetCardInLibrary {

    private static final FilterCard filter = new FilterArtifactCard("noncreature artifact cards");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    private final int value;

    SmeltingVatTarget(Ability source) {
        super(0, Integer.MAX_VALUE, filter);
        this.value = CardUtil
                .castStream(source.getCosts().stream(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .mapToInt(MageObject::getManaValue)
                .sum();
    }

    private SmeltingVatTarget(final SmeltingVatTarget target) {
        super(target);
        this.value = target.value;
    }

    @Override
    public SmeltingVatTarget copy() {
        return new SmeltingVatTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && card.getManaValue()
                + this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum() <= this.value;
    }
}

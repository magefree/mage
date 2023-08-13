package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;


/**
 * @author jeffwadsworth
 */
public final class BolassCitadel extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanents");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public BolassCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may play the top card of your library. If you cast a spell this way, pay life equal to its converted mana cost rather than pay its mana cost.
        this.addAbility(new SimpleStaticAbility(new BolassCitadelPlayTheTopCardEffect()));

        // {T}, Sacrifice ten nonland permanents: Each opponent loses 10 life.
        Ability ability = new SimpleActivatedAbility(new LoseLifeOpponentsEffect(10), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                10, 10, filter, true
        )));
        this.addAbility(ability);
    }

    private BolassCitadel(final BolassCitadel card) {
        super(card);
    }

    @Override
    public BolassCitadel copy() {
        return new BolassCitadel(this);
    }
}

class BolassCitadelPlayTheTopCardEffect extends AsThoughEffectImpl {

    BolassCitadelPlayTheTopCardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE,
                Duration.WhileOnBattlefield, Outcome.AIDontUseIt); // AI will need help with this
        staticText = "You may play lands and cast spells from the top of your library. If you cast a spell this way, "
                + "pay life equal to its mana value rather than pay its mana cost.";
    }

    private BolassCitadelPlayTheTopCardEffect(final BolassCitadelPlayTheTopCardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BolassCitadelPlayTheTopCardEffect copy() {
        return new BolassCitadelPlayTheTopCardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        // current card's part
        Card cardToCheck = game.getCard(objectId);
        if (cardToCheck == null) {
            return false;
        }

        // must be you
        if (!affectedControllerId.equals(source.getControllerId())) {
            return false;
        }

        // must be your card
        Player player = game.getPlayer(cardToCheck.getOwnerId());
        if (player == null || !player.getId().equals(affectedControllerId)) {
            return false;
        }

        // must be from your library
        Card topCard = player.getLibrary().getFromTop(game);
        if (topCard == null || !topCard.getId().equals(cardToCheck.getMainCard().getId())) {
            return false;
        }

        // allows to play/cast with alternative life cost
        if (!cardToCheck.isLand(game)) {
            PayLifeCost lifeCost = new PayLifeCost(cardToCheck.getSpellAbility().getManaCosts().manaValue());
            Costs newCosts = new CostsImpl();
            newCosts.add(lifeCost);
            newCosts.addAll(cardToCheck.getSpellAbility().getCosts());
            player.setCastSourceIdWithAlternateMana(cardToCheck.getId(), null, newCosts);
        }
        return true;
    }
}

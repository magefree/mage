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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;
import mage.abilities.costs.Cost;

/**
 * @author jeffwadsworth
 */
public final class BolassCitadel extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("nonland permanents");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public BolassCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{B}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);

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
        staticText = "You may play the top card of your library. If you cast a spell this way, "
                + "pay life equal to its converted mana cost rather than pay its mana cost.";
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
        Card cardOnTop = game.getCard(objectId);
        if (cardOnTop == null) {
            return false;
        }
        if (affectedControllerId.equals(source.getControllerId())
                && cardOnTop.isOwnedBy(source.getControllerId())) {
            Player controller = game.getPlayer(cardOnTop.getOwnerId());
            if (controller != null
                    && cardOnTop.equals(controller.getLibrary().getFromTop(game))) {
                // add the life cost first
                PayLifeCost cost = new PayLifeCost(cardOnTop.getManaCost().convertedManaCost());
                Costs costs = new CostsImpl();
                costs.add(cost);
                // check for additional costs that must be paid
                if (cardOnTop.getSpellAbility() != null) {
                    for (Cost additionalCost : cardOnTop.getSpellAbility().getCosts()) {
                        costs.add(additionalCost);
                    }
                }
                controller.setCastSourceIdWithAlternateMana(cardOnTop.getId(), null, costs);
                return true;
            }
        }
        return false;
    }
}

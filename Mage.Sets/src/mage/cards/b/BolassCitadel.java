package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
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
import mage.util.CardUtil;

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
        return applies(objectId, null, source, game, affectedControllerId);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Card cardToCheck = game.getCard(objectId);
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards

        if (cardToCheck != null
                && playerId.equals(source.getControllerId())
                && cardToCheck.isOwnedBy(source.getControllerId())) {
            Player controller = game.getPlayer(cardToCheck.getOwnerId());
            if (controller != null
                    && controller.getLibrary().getFromTop(game) != null
                    && objectId.equals(controller.getLibrary().getFromTop(game).getId())) {
                if (affectedAbility instanceof ActivatedAbility) {
                    ActivatedAbility activatedAbility = (ActivatedAbility) affectedAbility;
                    // add the life cost first
                    PayLifeCost cost = new PayLifeCost(activatedAbility.getManaCosts().convertedManaCost());
                    Costs costs = new CostsImpl();
                    costs.add(cost);
                    costs.addAll(activatedAbility.getCosts());
                    controller.setCastSourceIdWithAlternateMana(activatedAbility.getSourceId(), null, costs);
                    return true;
                }
            }
        }
        return false;
    }
}

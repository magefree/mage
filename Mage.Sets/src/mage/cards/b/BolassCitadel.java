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
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

import java.util.Objects;
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
        staticText = "You may play lands and cast spells from the top of your library. If you cast a spell this way, "
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
        if (!Objects.equals(source.getControllerId(), playerId)) {
            return false;
        }

        Player player = game.getPlayer(playerId);
        if (player != null) {
            Card topCard = player.getLibrary().getFromTop(game);
            UUID objectIdToCast = CardUtil.getMainCardId(game, objectId); // for adventure cards
            if (topCard == null || !topCard.getId().equals(objectIdToCast)) {
                return false;
            }

            if (topCard instanceof SplitCard || topCard instanceof ModalDoubleFacesCard) {
                // double faces cards
                Card card1;
                Card card2;
                if (topCard instanceof SplitCard) {
                    card1 = ((SplitCard) topCard).getLeftHalfCard();
                    card2 = ((SplitCard) topCard).getRightHalfCard();
                } else {
                    card1 = ((ModalDoubleFacesCard) topCard).getLeftHalfCard();
                    card2 = ((ModalDoubleFacesCard) topCard).getRightHalfCard();
                }

                // left
                if (!card1.isLand()) {
                    PayLifeCost lifeCost = new PayLifeCost(card1.getSpellAbility().getManaCosts().convertedManaCost());
                    Costs newCosts = new CostsImpl();
                    newCosts.add(lifeCost);
                    newCosts.addAll(card1.getSpellAbility().getCosts());
                    player.setCastSourceIdWithAlternateMana(card1.getId(), null, newCosts);
                }

                // right
                if (!card2.isLand()) {
                    PayLifeCost lifeCost = new PayLifeCost(card2.getSpellAbility().getManaCosts().convertedManaCost());
                    Costs newCosts = new CostsImpl();
                    newCosts.add(lifeCost);
                    newCosts.addAll(card2.getSpellAbility().getCosts());
                    player.setCastSourceIdWithAlternateMana(card2.getId(), null, newCosts);
                }
            } else {
                // other single face cards
                if (!topCard.isLand()) {
                    if (affectedAbility == null) {
                        affectedAbility = topCard.getSpellAbility();
                    } else {
                        objectIdToCast = affectedAbility.getSourceId();
                    }
                    PayLifeCost cost = new PayLifeCost(affectedAbility.getManaCosts().convertedManaCost());
                    Costs costs = new CostsImpl();
                    costs.add(cost);
                    costs.addAll(affectedAbility.getCosts());
                    player.setCastSourceIdWithAlternateMana(objectIdToCast, null, costs);
                }
            }
            return true;
        }
        return false;
    }
}

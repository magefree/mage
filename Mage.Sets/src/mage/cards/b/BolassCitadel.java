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
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.util.CardUtil;


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
        Player player = game.getPlayer(playerId);
        if (player != null) {
            Card topCard = player.getLibrary().getFromTop(game);
            UUID objectIdToCast = CardUtil.getMainCardId(game, objectId); // for adventure cards
            if (topCard == null || !topCard.getId().equals(objectIdToCast)) {
                return false;
            }
            if (!topCard.isLand()) {
                if (topCard instanceof SplitCard) {
                    SplitCardHalf leftCard = ((SplitCard) topCard).getLeftHalfCard();
                    PayLifeCost lifeCost = new PayLifeCost(leftCard.getSpellAbility().getManaCosts().convertedManaCost());
                    Costs leftCosts = new CostsImpl();
                    leftCosts.add(lifeCost);
                    leftCosts.addAll(leftCard.getSpellAbility().getCosts());
                    player.setCastSourceIdWithAlternateMana(leftCard.getId(), null, leftCosts);

                    SplitCardHalf rightCard = ((SplitCard) topCard).getRightHalfCard();
                    lifeCost = new PayLifeCost(rightCard.getSpellAbility().getManaCosts().convertedManaCost());
                    Costs rightCosts = new CostsImpl();
                    rightCosts.add(lifeCost);
                    rightCosts.addAll(rightCard.getSpellAbility().getCosts());
                    player.setCastSourceIdWithAlternateMana(rightCard.getId(), null, rightCosts);
                } else {
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

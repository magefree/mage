package mage.cards.u;

import java.util.UUID;


import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Codermann63
 */
public final class UnfinishedBusiness extends CardImpl {

    private static final FilterCard auraOrEquipmentFilter = new FilterCard("Aura or Equipment card");

    static {
        auraOrEquipmentFilter.add(Predicates.or(
                SubType.EQUIPMENT.getPredicate(),
                SubType.AURA.getPredicate()
        ));
    }

    public UnfinishedBusiness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(1,1, StaticFilters.FILTER_CARD_CREATURE));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, auraOrEquipmentFilter));
        // Return target creature card from your graveyard to the battlefield,
        // then return up to two target Aura and/or Equipment cards from your graveyard to the battlefield attached to that creature.
        this.getSpellAbility().addEffect(new UnfinishedBusinessEffect());
    }

    private UnfinishedBusiness(final UnfinishedBusiness card) {
        super(card);
    }

    @Override
    public UnfinishedBusiness copy() {
        return new UnfinishedBusiness(this);
    }
}

class UnfinishedBusinessEffect extends OneShotEffect{

    UnfinishedBusinessEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Return target creature card from your graveyard to the battlefield, then return up to two target Aura and/or Equipment cards from your graveyard to the battlefield attached to that creature. <i>(If the Auras can not enchant that creature, they remain in your graveyard.)</i>";
    }

    private UnfinishedBusinessEffect(final UnfinishedBusinessEffect effect) {super(effect);}

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer((source.getControllerId()));
        if (controller == null){
            return false;
        }

        // Return target creature from the graveyard to the battlefield
        Card targetCreature = game.getCard(source.getTargets().getFirstTarget());

        if (targetCreature != null){
            controller.moveCards(targetCreature, Zone.BATTLEFIELD, source, game);
            game.getState().processAction(game);
        }
        Permanent permanentCreature = targetCreature == null ? null : game.getPermanent(targetCreature.getId());

        // Target auras and/or equipment in your graveyard.
        Cards cardsInitial = new CardsImpl(source.getTargets().get(1).getTargets());
        if (cardsInitial.isEmpty()) {
            return false;
        }

        // Auras that cannot be attached to the creature stay in the graveyard
        // Create a list of legal cards to return
        Cards cards = new CardsImpl();
        for(UUID c: cardsInitial){
            if (game.getCard(c).hasSubtype(SubType.EQUIPMENT,game)){
                // always add equipment cards
                cards.add(c);
            }
            else if (permanentCreature != null &&
                    !permanentCreature.cantBeAttachedBy(game.getCard(c),source, game, false) &&
                    game.getCard(c).hasSubtype(SubType.AURA, game)){
                // only add auras if the creature has returned
                // only add auras that can be attached to creature
                cards.add(c);
            }
        }
        if (cards.isEmpty()){
            return false;
        }

        // Handle return of legal auras and equipment
        if (permanentCreature != null){
            cards.getCards(game)
                    .forEach(card -> game.getState().setValue("attachTo:" + card.getId(), permanentCreature));
        }
        controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
        if (permanentCreature != null){
            for(UUID id: cards){
                if (!permanentCreature.cantBeAttachedBy(game.getCard(id), source, game, true)){
                    permanentCreature.addAttachment(id, source, game);
                }
            }
        }
        return true;
    }

    @Override
    public UnfinishedBusinessEffect copy() {
        return new UnfinishedBusinessEffect(this);
    }
}
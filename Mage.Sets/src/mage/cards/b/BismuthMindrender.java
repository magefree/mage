package mage.cards.b;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.ApprovingObject;
import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author grimreap124
 */
public final class BismuthMindrender extends CardImpl {

    public BismuthMindrender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Bismuth Mindrender deals combat damage to a player, that player exiles cards from the top of their library until they exile a nonland card. You may cast that card by paying life equal to the spell's mana value rather than paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BismuthMindrenderEffect(),false, true));
    }

    private BismuthMindrender(final BismuthMindrender card) {
        super(card);
    }

    @Override
    public BismuthMindrender copy() {
        return new BismuthMindrender(this);
    }
}

class BismuthMindrenderEffect extends OneShotEffect {

    BismuthMindrenderEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles cards from the top of their library until they exile a nonland card. You may cast that card by paying life equal to the spell's mana value rather than paying its mana cost";
    }

    private BismuthMindrenderEffect(final BismuthMindrenderEffect effect) {
        super(effect);
    }

    @Override
    public BismuthMindrenderEffect copy() {
        return new BismuthMindrenderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();

        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
            game.informPlayers("Player is null");
            return false;
        }
        Card cardToCast = null;
        for (Card card : targetPlayer.getLibrary().getCards(game)) {
            targetPlayer.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                cardToCast = card;
                break;
            }
        }
        if (cardToCast == null) {
            return false;
        }
        List<Card> partsToCast = CardUtil.getCastableComponents(cardToCast, StaticFilters.FILTER_CARD, source, targetPlayer, game, null, false);
        String partsInfo = partsToCast
                .stream()
                .map(MageObject::getLogName)
                .collect(Collectors.joining(" or "));
        if (partsToCast.isEmpty()
                || !controller.chooseUse(
                Outcome.PlayForFree, "Cast spell by paying life equal to its mana value rather than paying its mana cost (" + partsInfo + ")?", source, game
        )) {
            return true;
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));

        // pay life
        // copied from BolassCitadelPlayTheTopCardEffect.applies
        PayLifeCost lifeCost = new PayLifeCost(cardToCast.getSpellAbility().getManaCosts().manaValue()); // TODO: Cost is most likely wrong for multi part cards. See Amped Raptor way for a rework.
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(lifeCost);
        newCosts.addAll(cardToCast.getSpellAbility().getCosts());
        controller.setCastSourceIdWithAlternateMana(cardToCast.getId(), null, newCosts);

        SpellAbility chosenAbility;
        chosenAbility = controller.chooseAbilityForCast(cardToCast, game, true);
        boolean result = false;
        if (chosenAbility != null) {
            result = controller.cast(
                    chosenAbility,
                    game, true, new ApprovingObject(source, game)
            );
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null));
        if (controller.isComputer() && !result) {
            cards.remove(cardToCast);
        }
        return result;
    }
}
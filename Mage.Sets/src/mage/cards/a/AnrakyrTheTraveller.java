package mage.cards.a;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author gravitybone
 * @author Aquid
 */
public final class AnrakyrTheTraveller extends CardImpl {

    public AnrakyrTheTraveller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT, CardType.CREATURE }, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lord of the Pyrrhian Legions — Whenever Anrakyr the Traveller attacks, you may cast an artifact spell from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.
        Ability ability = new AttacksTriggeredAbility(new AnrakyrTheTravellerEffect(), true);

        this.addAbility(ability.withFlavorWord("Lord of the Pyrrhian Legions"));
    }

    private AnrakyrTheTraveller(final AnrakyrTheTraveller card) {
        super(card);
    }

    @Override
    public AnrakyrTheTraveller copy() {
        return new AnrakyrTheTraveller(this);
    }
}

class AnrakyrTheTravellerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("an artifact spell");

    AnrakyrTheTravellerEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "you may cast " + filter.getMessage() + " from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.";
    }

    private AnrakyrTheTravellerEffect(final AnrakyrTheTravellerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        
        Set<Card> cards = player.getHand().getCards(filter, game);
        cards.addAll(player.getGraveyard().getCards(filter, game));
        
        Map<UUID, List<Card>> cardMap = new HashMap<>();
        for (Card card : cards) {
            List<Card> castableComponents = CardUtil.getCastableComponents(card, filter, source, player, game, null, false);
            if (!castableComponents.isEmpty()) {
                cardMap.put(card.getId(), castableComponents);
            }
        }      
        Card cardToCast;
        if (cardMap.isEmpty()) {
            return false;
        }
        Cards castableCards = new CardsImpl(cardMap.keySet());
        TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
        target.withNotTarget(true);
        player.choose(Outcome.Benefit, castableCards, target, source, game);
        cardToCast = castableCards.get(target.getFirstTarget(), game);

        if (cardToCast == null) {
            return false;
        }

        List<Card> partsToCast = cardMap.get(cardToCast.getId());
        String partsInfo = partsToCast
                .stream()
                .map(MageObject::getLogName)
                .collect(Collectors.joining(" or "));
        if (partsToCast.size() < 1
                || !player.chooseUse(
                Outcome.PlayForFree, "Cast spell by paying life equal to its mana value rather than paying its mana cost (" + partsInfo + ")?", source, game
        )) {
            return false;
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));
        
        // pay life
        // copied from BolassCitadelPlayTheTopCardEffect.applies
        PayLifeCost lifeCost = new PayLifeCost(cardToCast.getSpellAbility().getManaCosts().manaValue());
        Costs<Cost> newCosts = new CostsImpl<>();
        newCosts.add(lifeCost);
        newCosts.addAll(cardToCast.getSpellAbility().getCosts());
        player.setCastSourceIdWithAlternateMana(cardToCast.getId(), null, newCosts);

        ActivatedAbility chosenAbility;
        chosenAbility = player.chooseAbilityForCast(cardToCast, game, true);
        boolean result = false;
        if (chosenAbility instanceof SpellAbility) {
            result = player.cast(
                    (SpellAbility) chosenAbility,
                    game, true, new ApprovingObject(source, game)
            );
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null));
        if (player.isComputer() && !result) {
            cards.remove(cardToCast);
        }
        return result;
    }

    @Override
    public AnrakyrTheTravellerEffect copy() {
        return new AnrakyrTheTravellerEffect(this);
    }
}
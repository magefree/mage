package mage.cards.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.PlayLandAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CastFromHandForFreeEffect;
import mage.cards.AdventureCard;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardWithHalves;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactSpell;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.util.CardUtil;
import mage.util.CardUtil.SpellCastTracker;

/**
 * @author gravitybone
 * @author Aquid
 */
public final class AnrakyrTheTraveller extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard();


    public AnrakyrTheTraveller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT, CardType.CREATURE }, "{4}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NECRON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lord of the Pyrrhian Legions — Whenever Anrakyr the Traveller attacks, you may cast an artifact spell from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.
        Ability ability = new AttacksTriggeredAbility(new AnrakyrTheTravellerEffect(filter));

        this.addAbility(ability.withFlavorWord("Lord of the Pyrrhian Legions"));

    }

    private AnrakyrTheTraveller(final AnrakyrTheTraveller card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new AnrakyrTheTraveller(this);
    }
}

class AnrakyrTheTravellerEffect extends OneShotEffect {
    private final FilterCard filter;

    public AnrakyrTheTravellerEffect(FilterCard filter) {
        super(Outcome.AIDontUseIt);
        this.filter = filter;
        this.staticText = "you may cast an " + filter.getMessage() + " from your hand or graveyard by paying life equal to its mana value rather than paying its mana cost.";
    }

    protected AnrakyrTheTravellerEffect(final AnrakyrTheTravellerEffect effect) {
        super(effect);
        this.filter = effect.filter;
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
            List<Card> castableComponents = getCastableComponents(card, filter, source, player, game, false);
            if (!castableComponents.isEmpty()) {
                cardMap.put(card.getId(), castableComponents);
            }
        }      
        Card cardToCast;
        switch (cardMap.size()) {
            case 0:
                return false;
            default:
                Cards castableCards = new CardsImpl(cardMap.keySet());
                TargetCard target = new TargetCard(0, 1, Zone.ALL, filter);
                target.withNotTarget(true);
                player.choose(Outcome.Benefit, castableCards, target, source, game);
                cardToCast = castableCards.get(target.getFirstTarget(), game);
        }  

        if (cardToCast == null) {
            return false;
        }


        List<Card> partsToCast = cardMap.get(cardToCast.getId());
        String partsInfo = partsToCast
                .stream()
                .map(MageObject::getIdName)
                .collect(Collectors.joining(" or "));
        if (cardToCast == null
                || partsToCast.size() < 1
                || !player.chooseUse(
                Outcome.PlayForFree, "Cast spell without paying its mana cost (" + partsInfo + ")?", source, game
        )) {
            return false;
        }
        partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));
        
        // pay life
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

    private static List<Card> getCastableComponents(Card cardToCast, FilterCard filter, Ability source, Player player, Game game, boolean playLand) {
        UUID playerId = player.getId();
        List<Card> cards = new ArrayList<>();
        if (cardToCast instanceof CardWithHalves) {
            cards.add(((CardWithHalves) cardToCast).getLeftHalfCard());
            cards.add(((CardWithHalves) cardToCast).getRightHalfCard());
        } else if (cardToCast instanceof AdventureCard) {
            cards.add(cardToCast);
            cards.add(((AdventureCard) cardToCast).getSpellCard());
        } else {
            cards.add(cardToCast);
        }
        cards.removeIf(Objects::isNull);
        if (!playLand || !player.canPlayLand() || !game.isActivePlayer(playerId)) {
            cards.removeIf(card -> card.isLand(game));
        }
        cards.removeIf(card -> !filter.match(card, playerId, source, game));
    
        return cards;
    }

    @Override
    public AnrakyrTheTravellerEffect copy() {
        return new AnrakyrTheTravellerEffect(this);
    }
}
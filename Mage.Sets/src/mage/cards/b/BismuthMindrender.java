package mage.cards.b;

import mage.ApprovingObject;
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
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        this.addAbility(new MenaceAbility(false));

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

        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer == null) {
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

        List<Card> castableComponents = CardUtil.getCastableComponents(cardToCast, null, source, controller, game, null, false);
        if (castableComponents.isEmpty()) {
            return true;
        }
        String partsInfo = castableComponents
                .stream()
                .map(MageObject::getLogName)
                .collect(Collectors.joining(" or "));
        if (!controller.chooseUse(Outcome.PlayForFree, "Cast spell by paying life instead of mana (" + partsInfo + ")?", source, game)) {
            return true;
        }
        castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
        SpellAbility chosenAbility = controller.chooseAbilityForCast(cardToCast, game, true);
        if (chosenAbility != null) {
            Card faceCard = game.getCard(chosenAbility.getSourceId());
            if (faceCard != null) {
                // pay life instead of mana cost
                PayLifeCost lifeCost = new PayLifeCost(faceCard.getManaValue());
                Costs<Cost> newCosts = new CostsImpl<>();
                newCosts.add(lifeCost);
                newCosts.addAll(chosenAbility.getCosts());
                controller.setCastSourceIdWithAlternateMana(faceCard.getId(), null, newCosts);
                controller.cast(
                    chosenAbility, game, true,
                    new ApprovingObject(source, game)
                );
            }
        }
        castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), null));

        return true;
    }
}

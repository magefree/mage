package mage.cards.t;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author notgreat
 */
public final class TheInfamousCruelclaw extends CardImpl {

    public TheInfamousCruelclaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WEASEL);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever The Infamous Cruelclaw deals combat damage to a player, exile cards from the top of your library until you exile a nonland card. You may cast that card by discarding a card rather than paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new InfamousCruelclawEffect()));
    }

    private TheInfamousCruelclaw(final TheInfamousCruelclaw card) {
        super(card);
    }

    @Override
    public TheInfamousCruelclaw copy() {
        return new TheInfamousCruelclaw(this);
    }
}

//Based on Amped Raptor
class InfamousCruelclawEffect extends OneShotEffect {

    InfamousCruelclawEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile cards from the top of your library until you exile a nonland card. "
                + "You may cast that card by discarding a card rather than paying its mana cost.";
    }

    private InfamousCruelclawEffect(final InfamousCruelclawEffect effect) {
        super(effect);
    }

    @Override
    public InfamousCruelclawEffect copy() {
        return new InfamousCruelclawEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.getLibrary().hasCards()) {
            return false;
        }
        for (Card card : controller.getLibrary().getCards(game)) {
            controller.moveCards(card, Zone.EXILED, source, game);
            if (!card.isLand(game)) {
                List<Card> castableComponents = CardUtil.getCastableComponents(card, null, source, controller, game, null, false);
                if (castableComponents.isEmpty()) {
                    break;
                }
                String partsInfo = castableComponents
                        .stream()
                        .map(MageObject::getLogName)
                        .collect(Collectors.joining(" or "));
                if (!controller.chooseUse(Outcome.PlayForFree, "Cast spell by discarding a card instead of mana (" + partsInfo + ")?", source, game)) {
                    break;
                }
                castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
                SpellAbility chosenAbility = controller.chooseAbilityForCast(card, game, true);
                if (chosenAbility != null) {
                    Card faceCard = game.getCard(chosenAbility.getSourceId());
                    if (faceCard != null) {
                        // discard instead of mana cost
                        Costs<Cost> newCosts = new CostsImpl<>();
                        newCosts.add(new DiscardCardCost());
                        newCosts.addAll(chosenAbility.getCosts());
                        controller.setCastSourceIdWithAlternateMana(faceCard.getId(), null, newCosts);
                        controller.cast(
                                chosenAbility, game, true,
                                new ApprovingObject(source, game)
                        );
                    }
                }
                castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), null));
                break;
            }
        }
        return true;
    }
}
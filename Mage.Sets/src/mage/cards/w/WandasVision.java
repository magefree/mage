package mage.cards.w;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author notshauna
 */

public final class WandasVision extends CardImpl {

    public WandasVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        this.addAbility(new CastSecondSpellTriggeredAbility(new WandasVisionEffect()));
    }

    private WandasVision(final WandasVision card) {
        super(card);
    }

    @Override
    public WandasVision copy() {
        return new WandasVision(this);
    }

    class WandasVisionEffect extends OneShotEffect {

        WandasVisionEffect() {
            super(Outcome.PlayForFree);
            staticText = "exile cards from the top of your library until you exile a nonland card."
                    + "You may cast that card without paying its mana cost.";
        }

        private WandasVisionEffect(final WandasVisionEffect effect) {
            super(effect);
        }

        @Override
        public WandasVisionEffect copy() {
            return new WandasVisionEffect(this);
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
                    if (!controller.chooseUse(Outcome.PlayForFree, "You may cast that card without paying its mana cost.(" + partsInfo + ")?", source, game)) {
                        break;
                    }
                    castableComponents.forEach(partCard -> game.getState().setValue("PlayFromNotOwnHandZone" + partCard.getId(), Boolean.TRUE));
                    SpellAbility chosenAbility = controller.chooseAbilityForCast(card, game, true);
                    if (chosenAbility != null) {
                        Card faceCard = game.getCard(chosenAbility.getSourceId());
                        if (faceCard != null) {
                            Costs<Cost> newCosts = new CostsImpl<>();
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
}
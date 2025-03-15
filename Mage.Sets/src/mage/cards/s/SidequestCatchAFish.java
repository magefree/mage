package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;

/**
 * @author balazskristof
 */
public final class SidequestCatchAFish extends CardImpl {

    public SidequestCatchAFish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        
        this.secondSideCardClazz = mage.cards.c.CookingCampsite.class;

        // At the beginning of your upkeep, look at the top card of your library. If it's an artifact or creature card, you may reveal it and put it into your hand. If you put a card into your hand this way, create a Food token and transform this enchantment.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SidequestCatchAFishEffect()));
    }

    private SidequestCatchAFish(final SidequestCatchAFish card) {
        super(card);
    }

    @Override
    public SidequestCatchAFish copy() {
        return new SidequestCatchAFish(this);
    }
}

class SidequestCatchAFishEffect extends OneShotEffect {

    SidequestCatchAFishEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
            "If it's an artifact or creature card, you may reveal it and put it into your hand. " +
            "If you put a card into your hand this way, create a Food token and transform this enchantment.";
    }

    private SidequestCatchAFishEffect(final SidequestCatchAFishEffect effect) {
        super(effect);
    }

    @Override
    public SidequestCatchAFishEffect copy() {
        return new SidequestCatchAFishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        controller.lookAtCards("Top card of library", topCard, game);
        if (topCard.isArtifact(game) || topCard.isCreature(game)) {
            if (controller.chooseUse(Outcome.DrawCard, "Reveal " + topCard.getName() + " and put it into your hand?", source, game)) {
                controller.revealCards(source, new CardsImpl(topCard), game);
                controller.moveCards(topCard, Zone.HAND, source, game);
                new FoodToken().putOntoBattlefield(1, game, source);
                new TransformSourceEffect().apply(game, source);
                return true;
            }
        }
        return true;
    }
}
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class SarinthSteelseeker extends CardImpl {

    public SarinthSteelseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever an artifact enters the battlefield under your control, look at the top card of your library. If it's a land card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it into your graveyard.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new SarinthSteelseekerEffect(), StaticFilters.FILTER_PERMANENT_ARTIFACT_AN));
    }

    private SarinthSteelseeker(final SarinthSteelseeker card) {
        super(card);
    }

    @Override
    public SarinthSteelseeker copy() {
        return new SarinthSteelseeker(this);
    }
}

class SarinthSteelseekerEffect extends OneShotEffect {

    public SarinthSteelseekerEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. If it's a land card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it into your graveyard.";
    }

    private SarinthSteelseekerEffect(final SarinthSteelseekerEffect effect) {
        super(effect);
    }

    @Override
    public SarinthSteelseekerEffect copy() {
        return new SarinthSteelseekerEffect(this);
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
        if (topCard.isLand(game) && controller.chooseUse(Outcome.DrawCard, "Reveal " + topCard.getLogName() + " and put it into your hand?", source, game)) {
            controller.revealCards(source, new CardsImpl(topCard), game);
            controller.moveCards(topCard, Zone.HAND, source, game);
        } else if (controller.chooseUse(Outcome.Neutral, "Put " + topCard.getLogName() + " into your graveyard?", source, game)) {
            controller.moveCards(topCard, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}

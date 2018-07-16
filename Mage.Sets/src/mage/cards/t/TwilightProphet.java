
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TwilightProphet extends CardImpl {

    public TwilightProphet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ascend
        this.addAbility(new AscendAbility());

        // At the beginning of your upkeep, if you have the city's blessing, reveal the top card of your library and put it into your hand.
        // Each opponent loses X life and you gain X life, where X is that card's converted mana cost.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new TwilightProphetEffect(), TargetController.YOU, false), CitysBlessingCondition.instance,
                "At the beginning of your upkeep, if you have the city's blessing, reveal the top card of your library and put it into your hand."
                + "Each opponent loses X life and you gain X life, where X is that card's converted mana cost."));

    }

    public TwilightProphet(final TwilightProphet card) {
        super(card);
    }

    @Override
    public TwilightProphet copy() {
        return new TwilightProphet(this);
    }
}

class TwilightProphetEffect extends OneShotEffect {

    public TwilightProphetEffect() {
        super(Outcome.Benefit);
        this.staticText = "if you have the city's blessing, reveal the top card of your library and put it into your hand. "
                + "Each opponent loses X life and you gain X life, where X is that card's converted mana cost.";
    }

    public TwilightProphetEffect(final TwilightProphetEffect effect) {
        super(effect);
    }

    @Override
    public TwilightProphetEffect copy() {
        return new TwilightProphetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                controller.moveCards(card, Zone.HAND, source, game);
                game.applyEffects();
                int amount = card.getConvertedManaCost();
                if (amount > 0) {
                    new LoseLifeOpponentsEffect(amount).apply(game, source);
                    controller.gainLife(amount, game, source);
                }
            }
            return true;
        }
        return false;
    }
}

package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TwilightProphetEffect())
                .withInterveningIf(CitysBlessingCondition.instance).addHint(CitysBlessingHint.instance));
    }

    private TwilightProphet(final TwilightProphet card) {
        super(card);
    }

    @Override
    public TwilightProphet copy() {
        return new TwilightProphet(this);
    }
}

class TwilightProphetEffect extends OneShotEffect {

    TwilightProphetEffect() {
        super(Outcome.Benefit);
        this.staticText = "reveal the top card of your library and put it into your hand. "
                + "Each opponent loses X life and you gain X life, where X is that card's mana value.";
    }

    private TwilightProphetEffect(final TwilightProphetEffect effect) {
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
                game.processAction();
                int amount = card.getManaValue();
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

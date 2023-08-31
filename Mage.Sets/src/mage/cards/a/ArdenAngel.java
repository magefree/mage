package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArdenAngel extends CardImpl {

    public ArdenAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, if Arden Angel is in your graveyard, roll a four-sided die. If the result is 1, return Arden Angel from your graveyard to the battlefield.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new ArdenAngelEffect(), TargetController.YOU, false),
                ArdenAngelCondition.instance, "At the beginning of your upkeep, if {this} is in your graveyard, " +
                "roll a four-sided die. If the result is 1, return {this} from your graveyard to the battlefield."
        ));
    }

    private ArdenAngel(final ArdenAngel card) {
        super(card);
    }

    @Override
    public ArdenAngel copy() {
        return new ArdenAngel(this);
    }
}

enum ArdenAngelCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}

class ArdenAngelEffect extends OneShotEffect {

    ArdenAngelEffect() {
        super(Outcome.Benefit);
    }

    private ArdenAngelEffect(final ArdenAngelEffect effect) {
        super(effect);
    }

    @Override
    public ArdenAngelEffect copy() {
        return new ArdenAngelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.rollDice(outcome, source, game, 4) != 1) {
            return false;
        }
        Card card = game.getCard(source.getSourceId());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

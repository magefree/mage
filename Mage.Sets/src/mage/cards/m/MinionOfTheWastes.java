package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class MinionOfTheWastes extends CardImpl {

    public MinionOfTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}{B}");
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Minion of the Wastes enters the battlefield, pay any amount of life. The amount you pay can't be more than the total number of white nontoken permanents your opponents control plus the total number of white cards in their graveyards.
        this.addAbility(new AsEntersBattlefieldAbility(new MinionOfTheWastesEffect()));

        // Minion of the Wastes's power and toughness are each equal to the life paid as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the life paid as it entered the battlefield")));
    }

    public MinionOfTheWastes(final MinionOfTheWastes card) {
        super(card);
    }

    @Override
    public MinionOfTheWastes copy() {
        return new MinionOfTheWastes(this);
    }
}

class MinionOfTheWastesEffect extends OneShotEffect {

    public MinionOfTheWastesEffect() {
        super(Outcome.LoseLife);
        staticText = "pay any amount of life";
    }

    public MinionOfTheWastesEffect(final MinionOfTheWastesEffect effect) {
        super(effect);
    }

    @Override
    public MinionOfTheWastesEffect copy() {
        return new MinionOfTheWastesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int payAmount = controller.getAmount(0, controller.getLife(), "Pay any amount of life", game);
            controller.loseLife(payAmount, game, false);
            Card sourceCard = game.getCard(source.getSourceId());
            game.informPlayers((sourceCard != null ? sourceCard.getLogName() : "") + ": " + controller.getLogName() +
                    " pays " + payAmount + " life");
            game.addEffect(new SetPowerToughnessSourceEffect(payAmount, payAmount, Duration.Custom, SubLayer.SetPT_7b), source);
            return true;
        }
        return false;
    }
}

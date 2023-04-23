package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

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

    private MinionOfTheWastes(final MinionOfTheWastes card) {
        super(card);
    }

    @Override
    public MinionOfTheWastes copy() {
        return new MinionOfTheWastes(this);
    }
}

class MinionOfTheWastesEffect extends OneShotEffect {

    MinionOfTheWastesEffect() {
        super(Outcome.LoseLife);
        staticText = "pay any amount of life";
    }

    private MinionOfTheWastesEffect(final MinionOfTheWastesEffect effect) {
        super(effect);
    }

    @Override
    public MinionOfTheWastesEffect copy() {
        return new MinionOfTheWastesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        int payAmount = controller.getAmount(0, controller.getLife(), "Pay any amount of life", game);
        Cost cost = new PayLifeCost(payAmount);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        game.informPlayers((sourceCard != null ? sourceCard.getLogName() : "") + ": " + controller.getLogName() +
                " pays " + payAmount + " life");
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                payAmount, payAmount, Duration.Custom, SubLayer.CharacteristicDefining_7a
        ), source);
        permanent.addInfo("life paid", CardUtil.addToolTipMarkTags("Life paid: " + payAmount), game);
        return true;
    }
}

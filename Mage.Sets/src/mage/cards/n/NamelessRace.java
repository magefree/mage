package mage.cards.n;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author L_J
 */
public final class NamelessRace extends CardImpl {

    public NamelessRace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // As Nameless Race enters the battlefield, pay any amount of life. The amount you pay can't be more than the total number of white nontoken permanents your opponents control plus the total number of white cards in their graveyards.
        this.addAbility(new AsEntersBattlefieldAbility(new NamelessRaceEffect()));

        // Nameless Race's power and toughness are each equal to the life paid as it entered the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("{this}'s power and toughness are each equal to the life paid as it entered the battlefield")));
    }

    private NamelessRace(final NamelessRace card) {
        super(card);
    }

    @Override
    public NamelessRace copy() {
        return new NamelessRace(this);
    }
}

class NamelessRaceEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterPermanent("white nontoken permanents your opponents control");
    private static final FilterCard filter2
            = new FilterCard("white cards in their graveyards");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TokenPredicate.FALSE);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter2.add(new ColorPredicate(ObjectColor.WHITE));
        filter2.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    NamelessRaceEffect() {
        super(Outcome.LoseLife);
        staticText = "pay any amount of life. The amount you pay can't be more than " +
                "the total number of white nontoken permanents your opponents control " +
                "plus the total number of white cards in their graveyards";
    }

    private NamelessRaceEffect(final NamelessRaceEffect effect) {
        super(effect);
    }

    @Override
    public NamelessRaceEffect copy() {
        return new NamelessRaceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        int permanentsInPlay = new PermanentsOnBattlefieldCount(filter).calculate(game, source, null);
        int cardsInGraveyards = new CardsInAllGraveyardsCount(filter2).calculate(game, source, null);
        int maxAmount = Math.min(permanentsInPlay + cardsInGraveyards, controller.getLife());
        int payAmount = controller.getAmount(0, maxAmount, "Pay up to " + maxAmount + " life", game);
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

package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MinionToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author FenrisulfrX
 */
public final class PhyrexianProcessor extends CardImpl {

    public PhyrexianProcessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As {this} enters the battlefield, pay any amount of life.
        this.addAbility(new AsEntersBattlefieldAbility(new PhyrexianProcessorPayLifeEffect()));

        // {4}, {tap}: Create an X/X black Minion creature token, where X is the life paid as {this} entered the battlefield.
        Ability ability = new SimpleActivatedAbility(
                new PhyrexianProcessorCreateTokenEffect(), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private PhyrexianProcessor(final PhyrexianProcessor card) {
        super(card);
    }

    @Override
    public PhyrexianProcessor copy() {
        return new PhyrexianProcessor(this);
    }
}

class PhyrexianProcessorPayLifeEffect extends OneShotEffect {

    PhyrexianProcessorPayLifeEffect() {
        super(Outcome.LoseLife);
        staticText = "pay any amount of life.";
    }

    private PhyrexianProcessorPayLifeEffect(final PhyrexianProcessorPayLifeEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianProcessorPayLifeEffect copy() {
        return new PhyrexianProcessorPayLifeEffect(this);
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
        game.informPlayers((sourceCard != null ? sourceCard.getName() : "") + ": " + controller.getLogName() +
                " pays " + payAmount + " life.");
        String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game);
        game.getState().setValue(key, payAmount);
        permanent.addInfo("life paid", CardUtil.addToolTipMarkTags("Life paid: " + payAmount), game);
        return true;
    }
}

class PhyrexianProcessorCreateTokenEffect extends OneShotEffect {

    PhyrexianProcessorCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X black Phyrexian Minion creature token, " +
                "where X is the life paid as {this} entered the battlefield.";
    }

    private PhyrexianProcessorCreateTokenEffect(PhyrexianProcessorCreateTokenEffect ability) {
        super(ability);
    }

    @Override
    public PhyrexianProcessorCreateTokenEffect copy() {
        return new PhyrexianProcessorCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game, true);
        Object object = game.getState().getValue(key);
        if (object instanceof Integer) {
            int lifePaid = (int) object;
            MinionToken token = new MinionToken();
            token.setPower(lifePaid);
            token.setToughness(lifePaid);
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            return true;
        }
        return false;
    }
}
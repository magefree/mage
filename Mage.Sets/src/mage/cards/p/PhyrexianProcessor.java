package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PhyrexianProcessorEffect()));
        // {4}, {tap}: Create an X/X black Minion creature token, where X is the life paid as {this} entered the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PhyrexianProcessorCreateTokenEffect(), new ManaCostsImpl("{4}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public PhyrexianProcessor(final PhyrexianProcessor card) {
        super(card);
    }

    @Override
    public PhyrexianProcessor copy() {
        return new PhyrexianProcessor(this);
    }
}

class PhyrexianProcessorEffect extends OneShotEffect {

    public PhyrexianProcessorEffect() {
        super(Outcome.LoseLife);
        staticText = "Pay any amount of life.";
    }

    public PhyrexianProcessorEffect(final PhyrexianProcessorEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianProcessorEffect copy() {
        return new PhyrexianProcessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int payAmount = controller.getAmount(0, controller.getLife(), staticText, game);
            controller.loseLife(payAmount, game, false);
            Card sourceCard = game.getCard(source.getSourceId());
            game.informPlayers((sourceCard != null ? sourceCard.getName() : "") + ": " + controller.getLogName() +
                    " pays " + payAmount + " life.");
            String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game);
            game.getState().setValue(key, payAmount);
            return true;
        }
        return false;
    }
}

class PhyrexianProcessorCreateTokenEffect extends OneShotEffect {

    public PhyrexianProcessorCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X black Minion creature token";
    }

    public PhyrexianProcessorCreateTokenEffect(PhyrexianProcessorCreateTokenEffect ability) {
        super(ability);
    }

    @Override
    public PhyrexianProcessorCreateTokenEffect copy() {
        return new PhyrexianProcessorCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String key = CardUtil.getCardZoneString("lifePaid", source.getSourceId(), game);
        Object object = game.getState().getValue(key);
        if (object instanceof Integer) {
            int lifePaid = (int) object;
            MinionToken token = new MinionToken();
            token.getPower().modifyBaseValue(lifePaid);
            token.getToughness().modifyBaseValue(lifePaid);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}
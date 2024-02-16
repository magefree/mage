package mage.cards.s;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SproutbackTrudge extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0);

    public SproutbackTrudge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{G}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(9);
        this.toughness = new MageInt(7);

        // This spell costs {X} less to cast, where X is the amount of life you gained this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(ControllerGainedLifeCount.instance)
        ).addHint(ControllerGainedLifeCount.getHint()).setRuleAtTheTop(true), new PlayerGainedLifeWatcher());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of your end step, if you gained life this turn, you may cast Sproutback Trudge from your graveyard.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.GRAVEYARD, new SproutbackTrudgeEffect(), TargetController.YOU, condition, true
        ));
    }

    private SproutbackTrudge(final SproutbackTrudge card) {
        super(card);
    }

    @Override
    public SproutbackTrudge copy() {
        return new SproutbackTrudge(this);
    }
}

class SproutbackTrudgeEffect extends OneShotEffect {

    SproutbackTrudgeEffect() {
        super(Outcome.Benefit);
        staticText = "cast {this} from your graveyard";
    }

    private SproutbackTrudgeEffect(final SproutbackTrudgeEffect effect) {
        super(effect);
    }

    @Override
    public SproutbackTrudgeEffect copy() {
        return new SproutbackTrudgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (player == null || !(sourceObject instanceof Card)) {
            return false;
        }
        Card card = (Card) sourceObject;
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
        player.cast(
                player.chooseAbilityForCast(card, game, false),
                game, false, new ApprovingObject(source, game)
        );
        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        return true;
    }
}

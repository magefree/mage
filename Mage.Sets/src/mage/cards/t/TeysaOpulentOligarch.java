package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TeysaOpulentOligarch extends CardImpl {

    private static final Hint hint = new ValueHint("Opponents who lost life this turn", TeysaOpulentOligarchDynamicValue.instance);

    private static final FilterPermanent filterClue = new FilterPermanent();

    static {
        filterClue.add(TargetController.YOU.getControllerPredicate());
        filterClue.add(SubType.CLUE.getPredicate());
    }

    public TeysaOpulentOligarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your end step, investigate for each opponent who lost life this turn.
        this.addAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new InvestigateEffect(TeysaOpulentOligarchDynamicValue.instance)
                                .setText("investigate for each opponent who lost life this turn"),
                        TargetController.YOU,
                        false
                ).addHint(hint)
        );

        // Whenever a Clue you control is put into a graveyard from the battlefield, create a 1/1 white and black Spirit creature token with flying. This ability triggers only once each turn.
        TriggeredAbility trigger = new ZoneChangeAllTriggeredAbility(
                Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new CreateTokenEffect(new WhiteBlackSpiritToken()),
                filterClue,
                "Whenever a Clue you control is put into a graveyard from the battlefield, ",
                false
        );
        trigger.setTriggersOnceEachTurn(true);
        this.addAbility(trigger);
    }

    private TeysaOpulentOligarch(final TeysaOpulentOligarch card) {
        super(card);
    }

    @Override
    public TeysaOpulentOligarch copy() {
        return new TeysaOpulentOligarch(this);
    }
}

/**
 * derived from {@link mage.cards.s.StrefanMaurerProgenitor}
 */
enum TeysaOpulentOligarchDynamicValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player controller = game.getPlayer(sourceAbility.getControllerId());
        if (controller == null) {
            return 0;
        }

        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        int numOpponentsWhoLostLife = 0;

        if (watcher != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game) && watcher.getLifeLost(playerId) > 0) {
                    numOpponentsWhoLostLife++;
                }
            }
        }

        return numOpponentsWhoLostLife;
    }

    @Override
    public DynamicValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "opponents who lost life this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

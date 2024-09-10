
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class MirroredLotus extends CardImpl {

    public MirroredLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // Reflect {0} (As this enters the battlefield, each opponent may pay {0}. When they do, they create a token copy of this except it lacks this ability.)
        this.addAbility(new MirroredLotusAbility());

        // {tap}, Exile Black Lotus: Add three mana of any one color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private MirroredLotus(final MirroredLotus card) {
        super(card);
    }

    @Override
    public MirroredLotus copy() {
        return new MirroredLotus(this);
    }
}

// Is a class to be removed from the token.
class MirroredLotusAbility extends SimpleStaticAbility {
    MirroredLotusAbility() {
        super(Zone.ALL, new MirroredLotusReplacementEffect());
    }

    private MirroredLotusAbility(final MirroredLotusAbility ability) {
        super(ability);
    }

    @Override
    public MirroredLotusAbility copy() {
        return new MirroredLotusAbility(this);
    }
}

class MirroredLotusReplacementEffect extends ReplacementEffectImpl {

    MirroredLotusReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Reflect {0} <i>(As this enters the battlefield, each opponent may pay {0}. When they do, they create a token copy of this except it lacks this ability.)</i>";
    }

    private MirroredLotusReplacementEffect(final MirroredLotusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MirroredLotusReplacementEffect copy() {
        return new MirroredLotusReplacementEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Set<UUID> opponentsIds = game.getOpponents(source.getControllerId());

        for (UUID playerId : game.getPlayerList()) {
            if (!opponentsIds.contains(playerId)) {
                continue;
            }
            Player opponent = game.getPlayer(playerId);
            if (opponent == null) {
                continue;
            }

            Cost cost = new ManaCostsImpl<>("{0}");
            if (!cost.canPay(source, source, playerId, game)) {
                continue;
            }

            if (opponent.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + " for Reflect?", source, game)) {
                if (cost.pay(source, game, source, playerId, false)) {
                    game.informPlayers(opponent.getLogName() + " paid the Reflect cost");

                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(playerId);
                    effect.addAbilityClassesToRemoveFromTokens(MirroredLotusAbility.class);
                    effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));

                    ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                            effect, false,
                            "create a token copy of {this} except it lacks the Reflect ability"
                    );
                    reflexive.setControllerId(playerId);
                    game.fireReflexiveTriggeredAbility(reflexive, source);
                }
            }
        }
        return false;
    }
}

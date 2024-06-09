package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarTrainedSlasher extends CardImpl {

    public WarTrainedSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.WOLVERINE);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever War-Trained Slasher attacks a battle, double its power until end of turn.
        this.addAbility(new WarTrainedSlasherTriggeredAbility());
    }

    private WarTrainedSlasher(final WarTrainedSlasher card) {
        super(card);
    }

    @Override
    public WarTrainedSlasher copy() {
        return new WarTrainedSlasher(this);
    }
}

class WarTrainedSlasherTriggeredAbility extends TriggeredAbilityImpl {

    WarTrainedSlasherTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WarTrainedSlasherEffect());
    }

    private WarTrainedSlasherTriggeredAbility(final WarTrainedSlasherTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WarTrainedSlasherTriggeredAbility copy() {
        return new WarTrainedSlasherTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return Optional
                .ofNullable(this.getSourceId())
                .map(game.getCombat()::getDefenderId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(permanent -> permanent.isBattle(game))
                .orElse(false);
    }

    @Override
    public String getRule() {
        return "Whenever {this} attacks a battle, double its power until end of turn.";
    }
}

class WarTrainedSlasherEffect extends OneShotEffect {

    WarTrainedSlasherEffect() {
        super(Outcome.Benefit);
    }

    private WarTrainedSlasherEffect(final WarTrainedSlasherEffect effect) {
        super(effect);
    }

    @Override
    public WarTrainedSlasherEffect copy() {
        return new WarTrainedSlasherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power == 0) {
            return false;
        }
        game.addEffect(new BoostSourceEffect(power, 0, Duration.EndOfTurn), source);
        return true;
    }
}

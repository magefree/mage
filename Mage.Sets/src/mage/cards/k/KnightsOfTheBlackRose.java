package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KnightsOfTheBlackRose extends CardImpl {

    public KnightsOfTheBlackRose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Knights of the Black Rose enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect(), false));

        // Whenever an opponent becomes the monarch, if you were the monarch as the turn began, that player loses 2 life and you gain 2 life.
        Ability ability = new KnightsOfTheBlackRoseTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability, new KnightsOfTheBlackRoseWatcher());
    }

    private KnightsOfTheBlackRose(final KnightsOfTheBlackRose card) {
        super(card);
    }

    @Override
    public KnightsOfTheBlackRose copy() {
        return new KnightsOfTheBlackRose(this);
    }
}

class KnightsOfTheBlackRoseTriggeredAbility extends TriggeredAbilityImpl {

    public KnightsOfTheBlackRoseTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent becomes the monarch, if you were the monarch as the turn began, ");
    }

    public KnightsOfTheBlackRoseTriggeredAbility(final KnightsOfTheBlackRoseTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONARCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        KnightsOfTheBlackRoseWatcher watcher = game.getState().getWatcher(KnightsOfTheBlackRoseWatcher.class);
        return watcher != null && isControlledBy(watcher.getMonarchIdAtTurnStart());
    }

    @Override
    public KnightsOfTheBlackRoseTriggeredAbility copy() {
        return new KnightsOfTheBlackRoseTriggeredAbility(this);
    }
}

class KnightsOfTheBlackRoseWatcher extends Watcher {

    private UUID monarchIdAtTurnStart;

    public KnightsOfTheBlackRoseWatcher() {
        super(WatcherScope.GAME);
    }


    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case BEGINNING_PHASE_PRE:
                monarchIdAtTurnStart = game.getMonarchId();
        }
    }


    @Override
    public void reset() {
        super.reset();
        monarchIdAtTurnStart = null;
    }

    public UUID getMonarchIdAtTurnStart() {
        return monarchIdAtTurnStart;
    }
}

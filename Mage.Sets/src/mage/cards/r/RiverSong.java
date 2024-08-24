package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RiverSong extends CardImpl {

    public RiverSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Meet in Reverse -- You draw cards from the bottom of your library rather than the top.
        this.addAbility(new SimpleStaticAbility(new RiverSongDrawFromBottomEffect())
                .withFlavorWord("Meet in Reverse"));

        // Spoilers -- Whenever an opponent scries, surveils, or searches their library, put a +1/+1 counter on River Song.
        // Then River Song deals damage to that player equal to its power.
        TriggeredAbility trigger = new RiverSongTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        trigger.addEffect(new DamageTargetEffect(new SourcePermanentPowerCount(false))
                .setText("Then {this} deals damage to that player equal to its power"));
        this.addAbility(trigger.withFlavorWord("Spoilers"));
    }

    private RiverSong(final RiverSong card) {
        super(card);
    }

    @Override
    public RiverSong copy() {
        return new RiverSong(this);
    }
}

class RiverSongDrawFromBottomEffect extends ContinuousEffectImpl {

    RiverSongDrawFromBottomEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "you draw cards from the bottom of your library rather than the top";
    }

    private RiverSongDrawFromBottomEffect(final RiverSongDrawFromBottomEffect effect) {
        super(effect);
    }

    @Override
    public RiverSongDrawFromBottomEffect copy() {
        return new RiverSongDrawFromBottomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.setDrawsFromBottom(true);
        return true;
    }
}

class RiverSongTriggeredAbility extends TriggeredAbilityImpl {

    RiverSongTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever an opponent scries, surveils, or searches their library, ");
    }

    private RiverSongTriggeredAbility(final RiverSongTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiverSongTriggeredAbility copy() {
        return new RiverSongTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case SCRIED:
            case SURVEILED:
            case LIBRARY_SEARCHED:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        if (controller != null
                && controller.hasOpponent(event.getPlayerId(), game)
                && event.getPlayerId().equals(event.getTargetId())) { // searches own library
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }
}

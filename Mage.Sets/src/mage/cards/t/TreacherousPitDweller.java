
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 * @author noxx
 */
public final class TreacherousPitDweller extends CardImpl {

    public TreacherousPitDweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Undying
        this.addAbility(new UndyingAbility());

        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        this.addAbility(new TreacherousPitDwellerTriggeredAbility());
    }

    private TreacherousPitDweller(final TreacherousPitDweller card) {
        super(card);
    }

    @Override
    public TreacherousPitDweller copy() {
        return new TreacherousPitDweller(this);
    }
}

class TreacherousPitDwellerTriggeredAbility extends TriggeredAbilityImpl {

    public TreacherousPitDwellerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TreacherousPitDwellerEffect(), false);
        addTarget(new TargetOpponent());
        setTriggerPhrase("When {this} enters the battlefield from a graveyard, ");
    }

    public TreacherousPitDwellerTriggeredAbility(final TreacherousPitDwellerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId()) && ((EntersTheBattlefieldEvent) event).getFromZone() == Zone.GRAVEYARD;
    }

    @Override
    public TreacherousPitDwellerTriggeredAbility copy() {
        return new TreacherousPitDwellerTriggeredAbility(this);
    }
}

class TreacherousPitDwellerEffect extends ContinuousEffectImpl {

    public TreacherousPitDwellerEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    public TreacherousPitDwellerEffect(final TreacherousPitDwellerEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousPitDwellerEffect copy() {
        return new TreacherousPitDwellerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject permanent = source.getSourceObjectIfItStillExists(game); // it can also return Card object
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        if ((permanent instanceof Permanent)
                && targetOpponent != null) {
            return ((Permanent) permanent).changeControllerId(targetOpponent.getId(), game, source);
        } else {
            discard();
        }
        return false;
    }

}

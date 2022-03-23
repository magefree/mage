
package mage.cards.c;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public final class CityOfSolitude extends CardImpl {

    public CityOfSolitude(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Players can cast spells and activate abilities only during their own turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CityOfSolitudeEffect()));
    }

    private CityOfSolitude(final CityOfSolitude card) {
        super(card);
    }

    @Override
    public CityOfSolitude copy() {
        return new CityOfSolitude(this);
    }
}

class CityOfSolitudeEffect extends ContinuousRuleModifyingEffectImpl {

    CityOfSolitudeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Players can cast spells and activate abilities only during their own turns";
    }

    CityOfSolitudeEffect(final CityOfSolitudeEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        MageObject eventObject = game.getObject(event.getSourceId());
        if (sourceObject != null && eventObject != null) {
            return "You can cast or activate anability of " + eventObject.getIdName() + "  only during your own turns (" + sourceObject.getIdName() + "). ";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !game.isActivePlayer(event.getPlayerId());
    }

    @Override
    public CityOfSolitudeEffect copy() {
        return new CityOfSolitudeEffect(this);
    }
}

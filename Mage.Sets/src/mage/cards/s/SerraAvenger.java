
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;


/**
 * @author noxx
 */
public final class SerraAvenger extends CardImpl {

    public SerraAvenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // You can't cast Serra Avenger during your first, second, or third turns of the game.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantCastSerraAvengerEffect()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

    }

    private SerraAvenger(final SerraAvenger card) {
        super(card);
    }

    @Override
    public SerraAvenger copy() {
        return new SerraAvenger(this);
    }
}

class CantCastSerraAvengerEffect extends ContinuousRuleModifyingEffectImpl {

    public CantCastSerraAvengerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast this spell during your first, second, or third turns of the game";
    }

    private CantCastSerraAvengerEffect(final CantCastSerraAvengerEffect effect) {
        super(effect);
    }

    @Override
    public CantCastSerraAvengerEffect copy() {
        return new CantCastSerraAvengerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            Player controller = game.getPlayer(source.getControllerId());
            // it can be cast on other players turn 1 - 3 if some effect let allow you to do this
            if (controller != null && controller.getTurns() <= 3 && game.isActivePlayer(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }
}

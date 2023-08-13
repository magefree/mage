
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DarienKingOfKjeldor extends CardImpl {

    public DarienKingOfKjeldor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you're dealt damage, you may create that many 1/1 white Soldier creature tokens.
        this.addAbility(new DarienKingOfKjeldorTriggeredAbility());
    }

    private DarienKingOfKjeldor(final DarienKingOfKjeldor card) {
        super(card);
    }

    @Override
    public DarienKingOfKjeldor copy() {
        return new DarienKingOfKjeldor(this);
    }
}

class DarienKingOfKjeldorTriggeredAbility extends TriggeredAbilityImpl {

    public DarienKingOfKjeldorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DarienKingOfKjeldorEffect(), true);
    }

    public DarienKingOfKjeldorTriggeredAbility(final DarienKingOfKjeldorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DarienKingOfKjeldorTriggeredAbility copy() {
        return new DarienKingOfKjeldorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if ((event.getTargetId().equals(this.getControllerId()))) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you're dealt damage, you may create that many 1/1 white Soldier creature tokens.";
    }
}

class DarienKingOfKjeldorEffect extends OneShotEffect {

    public DarienKingOfKjeldorEffect() {
        super(Outcome.Benefit);
    }

    public DarienKingOfKjeldorEffect(final DarienKingOfKjeldorEffect effect) {
        super(effect);
    }

    @Override
    public DarienKingOfKjeldorEffect copy() {
        return new DarienKingOfKjeldorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int damage = (Integer) this.getValue("damageAmount");
            return new CreateTokenEffect(new SoldierToken(), damage).apply(game, source);
        }
        return false;
    }
}

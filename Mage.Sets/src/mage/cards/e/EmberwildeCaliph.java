package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class EmberwildeCaliph extends CardImpl {

    public EmberwildeCaliph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Emberwilde Caliph attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever Emberwilde Caliph deals damage, you lose that much life.
        this.addAbility(new EmberwildeCaliphTriggeredAbility());
    }

    private EmberwildeCaliph(final EmberwildeCaliph card) {
        super(card);
    }

    @Override
    public EmberwildeCaliph copy() {
        return new EmberwildeCaliph(this);
    }
}

class EmberwildeCaliphTriggeredAbility extends TriggeredAbilityImpl {

    public EmberwildeCaliphTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EmberwildeCaliphEffect(), false);
    }

    private EmberwildeCaliphTriggeredAbility(final EmberwildeCaliphTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmberwildeCaliphTriggeredAbility copy() {
        return new EmberwildeCaliphTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage, you lose that much life.";
    }
}

class EmberwildeCaliphEffect extends OneShotEffect {

    public EmberwildeCaliphEffect() {
        super(Outcome.LoseLife);
    }

    private EmberwildeCaliphEffect(final EmberwildeCaliphEffect effect) {
        super(effect);
    }

    @Override
    public EmberwildeCaliphEffect copy() {
        return new EmberwildeCaliphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controller.loseLife(amount, game, source, false);
            }
            return true;
        }
        return false;
    }
}

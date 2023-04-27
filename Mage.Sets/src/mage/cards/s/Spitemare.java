package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Spitemare extends CardImpl {

    public Spitemare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Spitemare is dealt damage, it deals that much damage to any target.
        Ability ability = new SpitemareTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private Spitemare(final Spitemare card) {
        super(card);
    }

    @Override
    public Spitemare copy() {
        return new Spitemare(this);
    }
}

class SpitemareTriggeredAbility extends TriggeredAbilityImpl {

    public SpitemareTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SpitemareEffect());
        setTriggerPhrase("Whenever {this} is dealt damage, ");
    }

    public SpitemareTriggeredAbility(final SpitemareTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SpitemareTriggeredAbility copy() {
        return new SpitemareTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            return true;
        }
        return false;
    }
}

class SpitemareEffect extends OneShotEffect {

    public SpitemareEffect() {
        super(Outcome.Damage);
        staticText = "it deals that much damage to any target";
    }

    public SpitemareEffect(final SpitemareEffect effect) {
        super(effect);
    }

    @Override
    public SpitemareEffect copy() {
        return new SpitemareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), source, game);
            return true;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage((Integer) this.getValue("damageAmount"), source.getSourceId(), source, game, false, true);
            return true;
        }
        return false;
    }
}

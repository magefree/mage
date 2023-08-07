
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class UlamogTheCeaselessHunger extends CardImpl {

    public UlamogTheCeaselessHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{10}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast Ulamog, the Ceaseless Hunger, exile two target permanents.
        this.addAbility(new UlamogExilePermanentsOnCastAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Ulamog attacks, defending player exiles the top twenty cards of their library.
        Effect effect = new UlamogExileLibraryEffect();
        effect.setText("defending player exiles the top twenty cards of their library");
        this.addAbility(new UlamogAttackTriggeredAbility(effect));
    }

    private UlamogTheCeaselessHunger(final UlamogTheCeaselessHunger card) {
        super(card);
    }

    @Override
    public UlamogTheCeaselessHunger copy() {
        return new UlamogTheCeaselessHunger(this);
    }
}

class UlamogExilePermanentsOnCastAbility extends TriggeredAbilityImpl {

    UlamogExilePermanentsOnCastAbility() {
        super(Zone.STACK, new ExileTargetEffect("exile two target permanents"));
        this.addTarget(new TargetPermanent(2, new FilterPermanent()));
        setTriggerPhrase("When you cast this spell, ");
    }

    UlamogExilePermanentsOnCastAbility(UlamogExilePermanentsOnCastAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        return this.getSourceId().equals(spell.getSourceId());
    }

    @Override
    public UlamogExilePermanentsOnCastAbility copy() {
        return new UlamogExilePermanentsOnCastAbility(this);
    }
}

class UlamogAttackTriggeredAbility extends TriggeredAbilityImpl {

    public UlamogAttackTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        setTriggerPhrase("Whenever {this} attacks, ");
    }

    public UlamogAttackTriggeredAbility(final UlamogAttackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public UlamogAttackTriggeredAbility copy() {
        return new UlamogAttackTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());
        if (sourcePermanent != null
                && event.getSourceId() != null
                && event.getSourceId().equals(this.getSourceId())) {
            UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
            return true;
        }
        return false;
    }
}

class UlamogExileLibraryEffect extends OneShotEffect {

    public UlamogExileLibraryEffect() {
        super(Outcome.Exile);
        this.staticText = "defending player exiles the top twenty cards of their library";
    }

    public UlamogExileLibraryEffect(final UlamogExileLibraryEffect effect) {
        super(effect);
    }

    @Override
    public UlamogExileLibraryEffect copy() {
        return new UlamogExileLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defender = game.getPlayer(targetPointer.getFirst(game, source));
        if (defender != null) {
            defender.moveCards(defender.getLibrary().getTopCards(game, 20), Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}

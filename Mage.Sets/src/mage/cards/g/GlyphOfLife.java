
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes & L_J
 */
public final class GlyphOfLife extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall creature");

    static {
        filter.add(SubType.WALL.getPredicate());
    }

    public GlyphOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Choose target Wall creature. Whenever that creature is dealt damage by an attacking creature this turn, you gain that much life.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new InfoEffect("Choose target Wall creature"));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GlyphOfLifeTriggeredAbility()));
    }

    private GlyphOfLife(final GlyphOfLife card) {
        super(card);
    }

    @Override
    public GlyphOfLife copy() {
        return new GlyphOfLife(this);
    }
}

class GlyphOfLifeTriggeredAbility extends DelayedTriggeredAbility {

    public GlyphOfLifeTriggeredAbility() {
        super(new GlyphOfLifeGainLifeEffect(), Duration.EndOfTurn, false);
        setTriggerPhrase("Whenever that creature is dealt damage by an attacking creature this turn, ");
    }

    public GlyphOfLifeTriggeredAbility(final GlyphOfLifeTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public GlyphOfLifeTriggeredAbility copy() {
        return new GlyphOfLifeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getFirstTarget())) {
            DamagedEvent damageEvent = (DamagedEvent) event;
            Permanent attackingCreature = game.getPermanentOrLKIBattlefield(damageEvent.getSourceId());
            if (attackingCreature != null && attackingCreature.isCreature(game) && attackingCreature.isAttacking()) {
                this.getEffects().get(0).setValue("damageAmount", event.getAmount());
                return true;
            }
        }
        return false;
    }
}

class GlyphOfLifeGainLifeEffect extends OneShotEffect {

    public GlyphOfLifeGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life";
    }

    public GlyphOfLifeGainLifeEffect(final GlyphOfLifeGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public GlyphOfLifeGainLifeEffect copy() {
        return new GlyphOfLifeGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife((Integer) this.getValue("damageAmount"), game, source);
        }
        return true;
    }

}

package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
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
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CurseOfTheForsaken extends CardImpl {

    public CurseOfTheForsaken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever a creature attacks enchanted player, its controller gains 1 life.
        this.addAbility(new CurseOfTheForsakenTriggeredAbility());

    }

    private CurseOfTheForsaken(final CurseOfTheForsaken card) {
        super(card);
    }

    @Override
    public CurseOfTheForsaken copy() {
        return new CurseOfTheForsaken(this);
    }
}

class CurseOfTheForsakenTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfTheForsakenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeTargetEffect(1), false);
    }

    public CurseOfTheForsakenTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public CurseOfTheForsakenTriggeredAbility(final CurseOfTheForsakenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player defender = game.getPlayer(event.getTargetId());
        if (defender != null) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment != null
                    && enchantment.isAttachedTo(defender.getId())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature attacks enchanted player, its controller gains 1 life.";
    }

    @Override
    public CurseOfTheForsakenTriggeredAbility copy() {
        return new CurseOfTheForsakenTriggeredAbility(this);
    }

}

package mage.cards.t;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import mage.filter.common.FilterCreatureCard;


/**
 * @author LevelX2
 */
public final class TeysaEnvoyOfGhosts extends CardImpl {

    public TeysaEnvoyOfGhosts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // protection from creatures
        this.addAbility(new ProtectionAbility(new FilterCreatureCard("creatures")));

        // Whenever a creature deals combat damage to you, destroy that creature. Create a 1/1 white and black Spirit creature token with flying.
        this.addAbility(new TeysaEnvoyOfGhostsTriggeredAbility());

    }

    private TeysaEnvoyOfGhosts(final TeysaEnvoyOfGhosts card) {
        super(card);
    }

    @Override
    public TeysaEnvoyOfGhosts copy() {
        return new TeysaEnvoyOfGhosts(this);
    }
}

class TeysaEnvoyOfGhostsTriggeredAbility extends TriggeredAbilityImpl {

    public TeysaEnvoyOfGhostsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new CreateTokenEffect(new WhiteBlackSpiritToken(), 1));
    }

    public TeysaEnvoyOfGhostsTriggeredAbility(final TeysaEnvoyOfGhostsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TeysaEnvoyOfGhostsTriggeredAbility copy() {
        return new TeysaEnvoyOfGhostsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent sourcePermanent = game.getPermanent(event.getSourceId());
        if (damageEvent.getPlayerId().equals(getControllerId())
                && damageEvent.isCombatDamage()
                && sourcePermanent != null
                && sourcePermanent.isCreature(game)) {
            game.getState().setValue(sourceId.toString(), sourcePermanent.getControllerId());
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, destroy that creature. Create a 1/1 white and black Spirit creature token with flying.";
    }

}

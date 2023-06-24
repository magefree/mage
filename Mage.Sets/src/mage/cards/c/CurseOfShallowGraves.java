package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
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
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CurseOfShallowGraves extends CardImpl {

    public CurseOfShallowGraves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever a player attacks enchanted player with one or more creatures, that attacking player may create a tapped 2/2 black Zombie creature token.
        this.addAbility(new CurseOfShallowTriggeredAbility());
    }

    private CurseOfShallowGraves(final CurseOfShallowGraves card) {
        super(card);
    }

    @Override
    public CurseOfShallowGraves copy() {
        return new CurseOfShallowGraves(this);
    }
}

class CurseOfShallowTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfShallowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfShallowEffect());
    }

    public CurseOfShallowTriggeredAbility(Effect effect, boolean optional, String text) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public CurseOfShallowTriggeredAbility(final CurseOfShallowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getCombat().getPlayerDefenders(game, false).contains(enchantment.getAttachedTo())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackingPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks enchanted player with one or more creatures, that attacking player may create a tapped 2/2 black Zombie creature token.";
    }

    @Override
    public CurseOfShallowTriggeredAbility copy() {
        return new CurseOfShallowTriggeredAbility(this);
    }

}

class CurseOfShallowEffect extends OneShotEffect {

    public CurseOfShallowEffect() {
        super(Outcome.Benefit);
        this.staticText = "that attacking player may create a tapped 2/2 black Zombie creature token";
    }

    public CurseOfShallowEffect(final CurseOfShallowEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfShallowEffect copy() {
        return new CurseOfShallowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player attacker = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (attacker != null && attacker.chooseUse(outcome, "create a tapped 2/2 black Zombie creature token?", source, game)) {
            Effect effect = new CreateTokenTargetEffect(new ZombieToken(), StaticValue.get(1), true, false);
            effect.setTargetPointer(targetPointer);
            return effect.apply(game, source);
        }
        return false;
    }
}

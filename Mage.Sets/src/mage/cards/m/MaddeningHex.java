package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import mage.util.RandomUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaddeningHex extends CardImpl {

    public MaddeningHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted player casts a noncreature spell, roll a d6. Maddening Hex deals damage to that player equal to the result. Then attach Maddening Hex to another one of your opponents chosen at random.
        this.addAbility(new MaddeningHexTriggeredAbility());
    }

    private MaddeningHex(final MaddeningHex card) {
        super(card);
    }

    @Override
    public MaddeningHex copy() {
        return new MaddeningHex(this);
    }
}

class MaddeningHexTriggeredAbility extends TriggeredAbilityImpl {

    public MaddeningHexTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MaddeningHexEffect());
    }

    private MaddeningHexTriggeredAbility(final MaddeningHexTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        Permanent permanent = getSourcePermanentIfItStillExists(game);
        if (spell == null
                || permanent == null
                || spell.isCreature(game)
                || !event.getPlayerId().equals(permanent.getAttachedTo())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public MaddeningHexTriggeredAbility copy() {
        return new MaddeningHexTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player casts a noncreature spell, roll a d6. " +
                "{this} deals damage to that player equal to the result. " +
                "Then attach {this} to another one of your opponents chosen at random.";
    }
}

class MaddeningHexEffect extends OneShotEffect {

    MaddeningHexEffect() {
        super(Outcome.Benefit);
    }

    private MaddeningHexEffect(final MaddeningHexEffect effect) {
        super(effect);
    }

    @Override
    public MaddeningHexEffect copy() {
        return new MaddeningHexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int result = controller.rollDice(outcome, source, game, 6);
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.damage(result, source.getSourceId(), source, game);
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (player != null) {
            opponents.remove(player.getId());
        }
        Player opponent = game.getPlayer(RandomUtil.randomFromCollection(opponents));
        if (opponent == null) {
            return true;
        }
        opponent.addAttachment(permanent.getId(), source, game);
        return true;
    }
}

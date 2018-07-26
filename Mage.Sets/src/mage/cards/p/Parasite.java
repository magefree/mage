package mage.cards.p;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.AttacksOrBlocksEnchantedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class Parasite extends CardImpl {

    public Parasite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(-1, -1)));

        // Whenever enchanted creature attacks or blocks, you may look at its controller's hand.
        this.addAbility(new OptionalAttacksOrBlocksEnchantedTriggeredAbility(Zone.BATTLEFIELD, new ParasiteEffect(), true));
    }

    public Parasite(final Parasite card) {
        super(card);
    }

    @Override
    public Parasite copy() {
        return new Parasite(this);
    }
}
class OptionalAttacksOrBlocksEnchantedTriggeredAbility extends TriggeredAbilityImpl {

    public OptionalAttacksOrBlocksEnchantedTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
    }

    public OptionalAttacksOrBlocksEnchantedTriggeredAbility(final OptionalAttacksOrBlocksEnchantedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public OptionalAttacksOrBlocksEnchantedTriggeredAbility copy() {
        return new OptionalAttacksOrBlocksEnchantedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED || event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        return enchantment != null && event.getSourceId().equals(enchantment.getAttachedTo());
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks or blocks, "+ super.getRule();
    }
}

class ParasiteEffect extends OneShotEffect {

    public ParasiteEffect() {
        super(Outcome.Benefit);
        staticText = "you may look at its controller's hand";
    }

    public ParasiteEffect(final ParasiteEffect effect) {
        super(effect);
    }

    @Override
    public ParasiteEffect copy() {
        return new ParasiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (you != null && enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature == null) {
                creature = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
            }
            if (creature != null) {
                Player player = game.getPlayer(creature.getControllerId());
                if (player != null) {
                    you.lookAtCards(sourceObject != null ? sourceObject.getIdName() : null, player.getHand(), game);
                    return true;
                }
            }
        }
        return false;
    }
}

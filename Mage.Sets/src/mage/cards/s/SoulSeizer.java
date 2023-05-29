package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SoulSeizer extends TransformingDoubleFacedCard {

    public SoulSeizer(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT}, "{3}{U}{U}",
                "Ghastly Haunting",
                new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.AURA}, "U"
        );
        this.getLeftHalfCard().setPT(1, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When Soul Seizer deals combat damage to a player, you may transform it. If you do, attach it to target creature that player controls.
        this.getLeftHalfCard().addAbility(new SoulSeizerTriggeredAbility());

        // Ghastly Haunting
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getRightHalfCard().getSpellAbility().addTarget(auraTarget);
        this.getRightHalfCard().getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.getRightHalfCard().addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));
    }

    private SoulSeizer(final SoulSeizer card) {
        super(card);
    }

    @Override
    public SoulSeizer copy() {
        return new SoulSeizer(this);
    }
}

class SoulSeizerTriggeredAbility extends TriggeredAbilityImpl {

    public SoulSeizerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SoulSeizerEffect(), true);
    }

    public SoulSeizerTriggeredAbility(SoulSeizerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SoulSeizerTriggeredAbility copy() {
        return new SoulSeizerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (!damageEvent.isCombatDamage() || !event.getSourceId().equals(this.getSourceId())) {
            return false;
        }
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
        filter.add(new ControllerIdPredicate(opponent.getId()));

        this.getTargets().clear();
        this.addTarget(new TargetCreaturePermanent(filter));
        return true;
    }

    @Override
    public String getRule() {
        return "When {this} deals combat damage to a player, you may transform it. " +
                "If you do, attach it to target creature that player controls.";
    }
}

class SoulSeizerEffect extends OneShotEffect {

    public SoulSeizerEffect() {
        super(Outcome.GainControl);
    }

    public SoulSeizerEffect(final SoulSeizerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || !permanent.transform(source, game)) {
            return false;
        }
        Permanent attachTo = game.getPermanent(getTargetPointer().getFirst(game, source));
        return attachTo != null && attachTo.addAttachment(permanent.getId(), source, game);
    }

    @Override
    public SoulSeizerEffect copy() {
        return new SoulSeizerEffect(this);
    }
}

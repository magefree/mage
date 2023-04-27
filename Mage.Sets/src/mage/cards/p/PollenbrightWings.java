
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class PollenbrightWings extends CardImpl {

    public PollenbrightWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield)));
        // Whenever enchanted creature deals combat damage to a player, create that many 1/1 green Saproling creature tokens.
        this.addAbility(new PollenbrightWingsAbility());
    }

    private PollenbrightWings(final PollenbrightWings card) {
        super(card);
    }

    @Override
    public PollenbrightWings copy() {
        return new PollenbrightWings(this);
    }
}

class PollenbrightWingsAbility extends TriggeredAbilityImpl {

    public PollenbrightWingsAbility() {
        super(Zone.BATTLEFIELD, new PollenbrightWingsEffect());
    }

    public PollenbrightWingsAbility(final PollenbrightWingsAbility ability) {
        super(ability);
    }

    @Override
    public PollenbrightWingsAbility copy() {
        return new PollenbrightWingsAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent damageSource = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && damageSource != null && damageSource.getAttachments().contains(this.getSourceId())) {
            game.getState().setValue("Damage_" + getSourceId(), damageEvent.getAmount());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals combat damage to a player, create that many 1/1 green Saproling creature tokens.";
    }
}

class PollenbrightWingsEffect extends OneShotEffect {

    public PollenbrightWingsEffect() {
        super(Outcome.Benefit);
        this.staticText = "create that many 1/1 green Saproling creature tokens";
    }

    public PollenbrightWingsEffect(final PollenbrightWingsEffect effect) {
        super(effect);
    }

    @Override
    public PollenbrightWingsEffect copy() {
        return new PollenbrightWingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) game.getState().getValue("Damage_" + source.getSourceId());
        if (damage != null) {
            return (new CreateTokenEffect(new SaprolingToken(), damage).apply(game, source));
        }
        return false;
    }
}

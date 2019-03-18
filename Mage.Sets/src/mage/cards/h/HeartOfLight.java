
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class HeartOfLight extends CardImpl {

    public HeartOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Prevent all damage that would be dealt to and dealt by enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new HeartOfLightEffect()));
    }

    public HeartOfLight(final HeartOfLight card) {
        super(card);
    }

    @Override
    public HeartOfLight copy() {
        return new HeartOfLight(this);
    }
}

class HeartOfLightEffect extends PreventionEffectImpl {

    public HeartOfLightEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all damage that would be dealt to and dealt by enchanted creature";
    }

    public HeartOfLightEffect(final HeartOfLightEffect effect) {
        super(effect);
    }

    @Override
    public HeartOfLightEffect copy() {
        return new HeartOfLightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            Permanent aura = game.getPermanent(source.getSourceId());
            if (aura != null && aura.getAttachedTo() != null) {
                if (event.getSourceId().equals(aura.getAttachedTo()) || event.getTargetId().equals(aura.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

}

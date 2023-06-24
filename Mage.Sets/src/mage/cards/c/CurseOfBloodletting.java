
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class CurseOfBloodletting extends CardImpl {

    public CurseOfBloodletting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget));

        // If a source would deal damage to enchanted player, it deals double that damage to that player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfBloodlettingEffect()));
    }

    private CurseOfBloodletting(final CurseOfBloodletting card) {
        super(card);
    }

    @Override
    public CurseOfBloodletting copy() {
        return new CurseOfBloodletting(this);
    }
}

class CurseOfBloodlettingEffect extends ReplacementEffectImpl {

    public CurseOfBloodlettingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a source would deal damage to enchanted player, it deals double that damage to that player instead";
    }

    public CurseOfBloodlettingEffect(final CurseOfBloodlettingEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfBloodlettingEffect copy() {
        return new CurseOfBloodlettingEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null &&
                enchantment.isAttachedTo(event.getTargetId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}

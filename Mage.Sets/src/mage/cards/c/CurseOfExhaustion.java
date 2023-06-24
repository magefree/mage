
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfExhaustion extends CardImpl {

    public CurseOfExhaustion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted player can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfExhaustionEffect()));
    }

    private CurseOfExhaustion(final CurseOfExhaustion card) {
        super(card);
    }

    @Override
    public CurseOfExhaustion copy() {
        return new CurseOfExhaustion(this);
    }
}

class CurseOfExhaustionEffect extends ContinuousRuleModifyingEffectImpl {

    public CurseOfExhaustionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Enchanted player can't cast more than one spell each turn";
    }

    public CurseOfExhaustionEffect(final CurseOfExhaustionEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfExhaustionEffect copy() {
        return new CurseOfExhaustionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Player player = game.getPlayer(enchantment.getAttachedTo());
                if (player != null && event.getPlayerId().equals(player.getId())) {
                    CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
                    if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) > 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

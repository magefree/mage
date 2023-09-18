
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Hallow extends CardImpl {

    public Hallow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Prevent all damage target spell would deal this turn. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new HallowPreventDamageByTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetSpell());

    }

    private Hallow(final Hallow card) {
        super(card);
    }

    @Override
    public Hallow copy() {
        return new Hallow(this);
    }
}

class HallowPreventDamageByTargetEffect extends PreventionEffectImpl {

    public HallowPreventDamageByTargetEffect(Duration duration) {
        super(duration, Integer.MAX_VALUE, false);
        staticText = "Prevent all damage target spell would deal this turn. You gain life equal to the damage prevented this way";
    }

    private HallowPreventDamageByTargetEffect(final HallowPreventDamageByTargetEffect effect) {
        super(effect);
    }

    @Override
    public HallowPreventDamageByTargetEffect copy() {
        return new HallowPreventDamageByTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = preventDamageAction(event, source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(preventionEffectData.getPreventedDamage(), game, source);
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject instanceof Spell){
                return this.getTargetPointer().getTargets(game, source).contains(mageObject.getId());
            }
        }
        return false;
    }

}

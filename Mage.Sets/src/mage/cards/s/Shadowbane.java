
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetSource;

/**
 * 
 * @tiera3 - copied from SamiteMinistration
 */
public final class Shadowbane extends CardImpl {

    public Shadowbane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Prevent all damage that would be dealt to you this turn by a source of your choice. Whenever damage from a black source is prevented this way this turn, you gain that much life.
        this.getSpellAbility().addEffect(new ShadowbaneEffect());
    }

    private Shadowbane(final Shadowbane card) {
        super(card);
    }

    @Override
    public Shadowbane copy() {
        return new Shadowbane(this);
    }

}

class ShadowbaneEffect extends PreventionEffectImpl {

    private final TargetSource targetSource;

    public ShadowbaneEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        this.staticText = "Prevent all damage that would be dealt to you this turn by a source of your choice. Whenever damage from a black source is prevented this way this turn, you gain that much life";
        this.targetSource = new TargetSource();
    }

    private ShadowbaneEffect(final ShadowbaneEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public ShadowbaneEffect copy() {
        return new ShadowbaneEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject != null && sourceObject.getColor(game).isBlack()) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(preventionData.getPreventedDamage(), game, source);
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }
}

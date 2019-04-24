
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class StonewiseFortifier extends CardImpl {

    public StonewiseFortifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{W}: Prevent all damage that would be dealt to Stonewise Fortifier by target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StonewiseFortifierPreventAllDamageToEffect(), new ManaCostsImpl("{4}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public StonewiseFortifier(final StonewiseFortifier card) {
        super(card);
    }

    @Override
    public StonewiseFortifier copy() {
        return new StonewiseFortifier(this);
    }
}

class StonewiseFortifierPreventAllDamageToEffect extends PreventionEffectImpl {

    public StonewiseFortifierPreventAllDamageToEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent all damage that would be dealt to {this} by target creature this turn";
    }

    public StonewiseFortifierPreventAllDamageToEffect(final StonewiseFortifierPreventAllDamageToEffect effect) {
        super(effect);
    }

    @Override
    public StonewiseFortifierPreventAllDamageToEffect copy() {
        return new StonewiseFortifierPreventAllDamageToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, event.getTargetId(), event.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            int preventedDamage = event.getAmount();
            MageObject damageSource = game.getObject(event.getSourceId());
            MageObject preventionSource = game.getObject(source.getSourceId());
            if (damageSource != null && preventionSource != null) {
                StringBuilder message = new StringBuilder(preventedDamage).append(" damage from ");
                message.append(damageSource.getName()).append(" prevented ");
                message.append('(').append(preventionSource).append(')');
                game.informPlayers(message.toString());
            }
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, event.getTargetId(), source.getSourceId(), source.getControllerId(), preventedDamage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId())) {
            if (event.getSourceId().equals(targetPointer.getFirst(game, source))) {
                return true;
            }
        }
        return false;
    }

}

package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.PreventDamageEvent;
import mage.game.events.PreventedDamageEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StonewiseFortifier extends CardImpl {

    public StonewiseFortifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {4}{W}: Prevent all damage that would be dealt to Stonewise Fortifier by target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StonewiseFortifierPreventAllDamageToEffect(), new ManaCostsImpl("{4}{W}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private StonewiseFortifier(final StonewiseFortifier card) {
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
        GameEvent preventEvent = new PreventDamageEvent(event.getTargetId(), event.getSourceId(), source, source.getControllerId(), event.getAmount(), ((DamageEvent) event).isCombatDamage());
        if (!game.replaceEvent(preventEvent)) {
            int preventedDamage = event.getAmount();
            MageObject damageSource = game.getObject(event.getSourceId());
            MageObject preventionSource = game.getObject(source);
            if (damageSource != null && preventionSource != null) {
                String message = " damage from " +
                        damageSource.getName() + " prevented " +
                        '(' + preventionSource + ')';
                game.informPlayers(message);
            }
            event.setAmount(0);
            game.fireEvent(new PreventedDamageEvent(event.getTargetId(), source.getSourceId(), source, source.getControllerId(), preventedDamage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId())) {
            return event.getSourceId().equals(targetPointer.getFirst(game, source));
        }
        return false;
    }

}

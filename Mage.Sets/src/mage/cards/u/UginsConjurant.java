package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.common.SimpleStaticAbility;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author antoni-g
 */
public final class UginsConjurant extends CardImpl {

    public UginsConjurant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}");
        
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Ugin’s Conjurant enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())));
        // If damage would be dealt to Ugin’s Conjurant while it has a +1/+1 counter on it, prevent that damage and remove that many +1/+1 counters from Ugin’s Conjurant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UginsConjurantPrevention()));
    }

    private UginsConjurant(final UginsConjurant card) {
        super(card);
    }

    @Override
    public UginsConjurant copy() {
        return new UginsConjurant(this);
    }

    static class UginsConjurantPrevention extends PreventionEffectImpl {

        public UginsConjurantPrevention() {
            super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
            staticText = "If damage would be dealt to {this}, prevent that damage and remove that many +1/+1 counters from it";
        }

        public UginsConjurantPrevention(final UginsConjurantPrevention effect) {
            super(effect);
        }

        @Override
        public UginsConjurantPrevention copy() {
            return new UginsConjurantPrevention(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            int damage = event.getAmount();
            preventDamageAction(event, source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.removeCounters(CounterType.P1P1.createInstance(damage), game); //MTG ruling Protean Hydra loses counters even if the damage isn't prevented
            }
            return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (super.applies(event, source, game)) {
                if (event.getTargetId().equals(source.getSourceId())) {
                    return true;
                }
            }
            return false;
        }

    }
}

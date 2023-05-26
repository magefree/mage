
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class SzadekLordOfSecrets extends CardImpl {

    public SzadekLordOfSecrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If Szadek, Lord of Secrets would deal combat damage to a player, instead put that many +1/+1 counters on Szadek and that player puts that many cards from the top of their library into their graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SzadekLordOfSecretsEffect()));

    }

    private SzadekLordOfSecrets(final SzadekLordOfSecrets card) {
        super(card);
    }

    @Override
    public SzadekLordOfSecrets copy() {
        return new SzadekLordOfSecrets(this);
    }
}

class SzadekLordOfSecretsEffect extends ReplacementEffectImpl {

    SzadekLordOfSecretsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If {this} would deal combat damage to a player, instead put that many +1/+1 counters on {this} and that player mills that many cards";
    }

    SzadekLordOfSecretsEffect(final SzadekLordOfSecretsEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        Player damagedPlayer = game.getPlayer(damageEvent.getTargetId());

        if (damageEvent.isCombatDamage()) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(damageEvent.getAmount()), source.getControllerId(), source, game);
                if (damagedPlayer != null) {
                    damagedPlayer.millCards(damageEvent.getAmount(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            if (damageEvent.isCombatDamage()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SzadekLordOfSecretsEffect copy() {
        return new SzadekLordOfSecretsEffect(this);
    }
}

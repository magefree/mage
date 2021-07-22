
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class WhisperingSpecter extends CardImpl {

    public WhisperingSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SPECTER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeSourceEffect(), true, true);
        ability.addEffect(new WhisperingSpecterEffect());
        this.addAbility(ability);
    }

    private WhisperingSpecter(final WhisperingSpecter card) {
        super(card);
    }

    @Override
    public WhisperingSpecter copy() {
        return new WhisperingSpecter(this);
    }
}

class WhisperingSpecterEffect extends OneShotEffect {
    WhisperingSpecterEffect() {
        super(Outcome.Discard);
        staticText = "If you do, that player discards a card for each poison counter they have";
    }

    WhisperingSpecterEffect(final WhisperingSpecterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int value = player.getCounters().getCount(CounterType.POISON);
            if (value > 0) {
                player.discard(value, false, false, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public WhisperingSpecterEffect copy() {
        return new WhisperingSpecterEffect(this);
    }
}

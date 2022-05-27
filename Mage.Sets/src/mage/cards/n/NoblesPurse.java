package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NoblesPurse extends CardImpl {

    public NoblesPurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Noble's Purse enters the battlefield tapped and with three coin counters on it.
        Ability ability = new EntersBattlefieldAbility(
                new TapSourceEffect(true), false, null,
                "{this} enters the battlefield tapped and with three coin counters on it.", null
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.COIN.createInstance(3)));
        this.addAbility(ability);

        // {T}, Remove a coin counter from Noble's Purse: Create a Treasure token.
        ability = new SimpleActivatedAbility(new CreateTokenEffect(new TreasureToken()), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.COIN.createInstance()));
        this.addAbility(ability);
    }

    private NoblesPurse(final NoblesPurse card) {
        super(card);
    }

    @Override
    public NoblesPurse copy() {
        return new NoblesPurse(this);
    }
}

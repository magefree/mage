package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalaGreeters extends CardImpl {

    public GalaGreeters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Alliance — Whenever another creature enters the battlefield under your control, choose one that hasn't been chosen this turn—
        // • Put a +1/+1 counter on Gala Greeters.
        Ability ability = new AllianceAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.getModes().setEachModeOnlyOnce(true);
        ability.getModes().setResetEachTurn(true);

        // • Create a tapped Treasure token.
        ability.addMode(new Mode(new CreateTokenEffect(new TreasureToken(), 1, true, false)));

        // • You gain 2 life.
        ability.addMode(new Mode(new GainLifeEffect(2)));
        this.addAbility(ability);
    }

    private GalaGreeters(final GalaGreeters card) {
        super(card);
    }

    @Override
    public GalaGreeters copy() {
        return new GalaGreeters(this);
    }
}

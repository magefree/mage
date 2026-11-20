package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainMichaelBurnham extends CardImpl {

    public CaptainMichaelBurnham(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Landfall -- Whenever a land you control enters, Captain Burnham deals 1 damage to each opponent. If this is the second time this ability has resolved this turn, put a +1/+1 counter on Captain Burnham.
        Ability ability = new LandfallAbility(new DamagePlayersEffect(1, TargetController.OPPONENT));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(2, new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private CaptainMichaelBurnham(final CaptainMichaelBurnham card) {
        super(card);
    }

    @Override
    public CaptainMichaelBurnham copy() {
        return new CaptainMichaelBurnham(this);
    }
}

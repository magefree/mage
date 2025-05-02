package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.AfflictAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

public final class AmmitEternal extends CardImpl {

    public AmmitEternal(UUID ownerId, CardSetInfo cardSetInfo) {
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        subtype.add(SubType.ZOMBIE);
        subtype.add(SubType.CROCODILE);
        subtype.add(SubType.DEMON);
        power = new MageInt(5);
        toughness = new MageInt(5);

        // Afflict 3 (Whenever this creature becomes blocked, defending player loses 3 life.)
        this.addAbility(new AfflictAbility(3));

        // Whenever an opponent casts a spell, put a -1/-1 counter on Ammit Eternal.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance()), false));

        // Whenever Ammit Eternal deals combat damage to a player, remove all -1/-1 counters from it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RemoveAllCountersSourceEffect(CounterType.M1M1), false));
    }

    private AmmitEternal(final AmmitEternal ammitEternal) {
        super(ammitEternal);
    }

    @Override
    public AmmitEternal copy() {
        return new AmmitEternal(this);
    }
}

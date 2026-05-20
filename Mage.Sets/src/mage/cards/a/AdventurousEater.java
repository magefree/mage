package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AdventurousEater extends PrepareCard {

    public AdventurousEater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}", "Have a Bite", CardType.SORCERY, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Have a Bite
        // Sorcery {B}
        // Put a +1/+1 counter on target creature. You gain 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private AdventurousEater(final AdventurousEater card) {
        super(card);
    }

    @Override
    public AdventurousEater copy() {
        return new AdventurousEater(this);
    }
}

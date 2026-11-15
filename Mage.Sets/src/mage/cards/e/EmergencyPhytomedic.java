package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EmergencyPhytomedic extends PrepareCard {

    public EmergencyPhytomedic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G/W}", "Seed Suture", new CardType[]{CardType.SORCERY}, "{G/W}");

        this.subtype.add(SubType.DRYAD);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Seed Suture
        // Sorcery {G/W}
        // Put a +1/+1 counter on target creature. You gain 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EmergencyPhytomedic(final EmergencyPhytomedic card) {
        super(card);
    }

    @Override
    public EmergencyPhytomedic copy() {
        return new EmergencyPhytomedic(this);
    }
}

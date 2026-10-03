package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlossomBlessedAngel extends PrepareCard {

    public BlossomBlessedAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}", "Seed Suture", new CardType[]{CardType.SORCERY}, "{G/W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // Seed Suture
        // Sorcery {G/W}
        // Put a +1/+1 counter on target creature. You gain 1 life.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    private BlossomBlessedAngel(final BlossomBlessedAngel card) {
        super(card);
    }

    @Override
    public BlossomBlessedAngel copy() {
        return new BlossomBlessedAngel(this);
    }
}

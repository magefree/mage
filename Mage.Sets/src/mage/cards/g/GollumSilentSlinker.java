package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GollumSilentSlinker extends AdventureCard {

    public GollumSilentSlinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{3}{B}", "Meager Meal", "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Meager Meal
        // Put a +1/+1 on up to one target creature. Target player gains 2 life.
        this.getSpellCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellCard().getSpellAbility().addEffect(new GainLifeTargetEffect(2)
            .setTargetPointer(new SecondTargetPointer())
        );
        this.getSpellCard().getSpellAbility().addTarget(new TargetPlayer());

        this.finalizeAdventure();
    }

    private GollumSilentSlinker(final GollumSilentSlinker card) {
        super(card);
    }

    @Override
    public GollumSilentSlinker copy() {
        return new GollumSilentSlinker(this);
    }
}

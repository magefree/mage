package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SymbioteSpiderMan extends CardImpl {

    public SymbioteSpiderMan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever this creature deals combat damage to a player, look at that many cards from the top of your library. Put one of them into your hand and the rest into your graveyard.
        this.addAbility(makeAbility());

        // Find New Host -- {2}{U/B}, Exile this card from your graveyard: Put a +1/+1 counter on target creature you control. It gains this card's other abilities. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{2}{U/B}")
        );
        ability.addEffect(new GainAbilityTargetEffect(makeAbility(), Duration.Custom)
                .setText("It gains this card's other abilities"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Find New Host"));
    }

    private SymbioteSpiderMan(final SymbioteSpiderMan card) {
        super(card);
    }

    @Override
    public SymbioteSpiderMan copy() {
        return new SymbioteSpiderMan(this);
    }

    private static Ability makeAbility() {
        return new DealsCombatDamageToAPlayerTriggeredAbility(new LookLibraryAndPickControllerEffect(
                SavedDamageValue.MANY, 1, PutCards.HAND, PutCards.GRAVEYARD
        ));
    }
}

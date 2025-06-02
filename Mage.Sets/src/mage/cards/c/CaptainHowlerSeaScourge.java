package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.SavedDiscardValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Jmlundeen
 */
public final class CaptainHowlerSeaScourge extends CardImpl {
    private static final DynamicValue powerValue = new MultipliedValue(SavedDiscardValue.MANY, 2);

    public CaptainHowlerSeaScourge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHARK);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ward--{2}, Pay 2 life.
        CompositeCost cost = new CompositeCost(new ManaCostsImpl<>("{2}"), new PayLifeCost(2), "{2}, Pay 2 life");
        this.addAbility(new WardAbility(cost, false));

        // Whenever you discard one or more cards, target creature gets +2/+0 until end of turn for each card discarded this way. Whenever that creature deals combat damage to a player this turn, you draw a card.
        Ability ability = new DiscardOneOrMoreCardsTriggeredAbility(
                new BoostTargetEffect(powerValue, StaticValue.get(0))
                        .setText("target creature gets +2/+0 until end of turn for each card discarded this way")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ))
                .setText("Whenever that creature deals combat damage to a player this turn, you draw a card")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CaptainHowlerSeaScourge(final CaptainHowlerSeaScourge card) {
        super(card);
    }

    @Override
    public CaptainHowlerSeaScourge copy() {
        return new CaptainHowlerSeaScourge(this);
    }
}
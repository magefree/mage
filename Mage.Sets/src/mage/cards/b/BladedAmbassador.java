package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BladedAmbassador extends CardImpl {

    public BladedAmbassador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bladed Ambassador enters the battlefield with an oil counter on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.OIL.createInstance()));

        // {1}, Remove an oil counter from Bladed Ambassador: Bladed Ambassador gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new GenericManaCost(1));
        ability.addCost(new RemoveCountersSourceCost(CounterType.OIL.createInstance()));
        this.addAbility(ability);
    }

    private BladedAmbassador(final BladedAmbassador card) {
        super(card);
    }

    @Override
    public BladedAmbassador copy() {
        return new BladedAmbassador(this);
    }
}

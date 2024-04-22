package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.mana.AddManaToManaPoolSourceControllerEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GaladrielLightOfValinor extends CardImpl {

    public GaladrielLightOfValinor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Alliance — Whenever another creature enters the battlefield under your control, choose one that hasn't been chosen this turn —
        // • Add {G}{G}{G}.
        Ability ability = new AllianceAbility(new AddManaToManaPoolSourceControllerEffect(Mana.GreenMana(3)));
        ability.setModeTag("add mana");
        ability.getModes().setLimitUsageByOnce(true);

        // • Put a +1/+1 counter on each creature you control.
        ability.addMode(
                new Mode(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE))
                        .setModeTag("put +1/+1 counters")
        );

        // • Scry 2, then draw a card.
        Mode mode = new Mode(new ScryEffect(2, false)).setModeTag("scry and draw");
        mode.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        ability.addMode(mode);

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private GaladrielLightOfValinor(final GaladrielLightOfValinor card) {
        super(card);
    }

    @Override
    public GaladrielLightOfValinor copy() {
        return new GaladrielLightOfValinor(this);
    }
}

package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author muz
 */
public final class ShimmeringCitadel extends CardImpl {

    public ShimmeringCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Exhaust -- {3}{W}{U}, {T}: You draw two cards and gain 1 life. Put a depletion counter on this land.
        Ability exhaustAbility = new ExhaustAbility(
            new DrawCardSourceControllerEffect(2, true),
            new ManaCostsImpl<>("{3}{W}{U}")
        );
        exhaustAbility.addCost(new TapSourceCost());
        exhaustAbility.addEffect(new GainLifeEffect(1).setText("and gain 1 life"));
        exhaustAbility.addEffect(new AddCountersSourceEffect(CounterType.DEPLETION.createInstance()));
        this.addAbility(exhaustAbility);
    }

    private ShimmeringCitadel(final ShimmeringCitadel card) {
        super(card);
    }

    @Override
    public ShimmeringCitadel copy() {
        return new ShimmeringCitadel(this);
    }
}

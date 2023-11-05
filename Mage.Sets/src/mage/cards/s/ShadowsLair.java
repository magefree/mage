package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadowsLair extends CardImpl {

    public ShadowsLair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);
        this.nightCard = true;

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {B}, {T}, Remove a dread counter from Shadows' Lair: You draw a card and you lose 1 life.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1, "you"), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.DREAD.createInstance()));
        ability.addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ShadowsLair(final ShadowsLair card) {
        super(card);
    }

    @Override
    public ShadowsLair copy() {
        return new ShadowsLair(this);
    }
}

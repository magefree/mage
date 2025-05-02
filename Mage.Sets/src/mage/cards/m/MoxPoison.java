package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class MoxPoison extends CardImpl {

    public MoxPoison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{0}");

        // {T}: Add one mana of any color. You get two poison counters.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(2), TargetController.YOU
        ));
        this.addAbility(ability);
    }

    private MoxPoison(final MoxPoison card) {
        super(card);
    }

    @Override
    public MoxPoison copy() {
        return new MoxPoison(this);
    }
}

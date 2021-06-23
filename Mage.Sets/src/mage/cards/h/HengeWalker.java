package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.AdamantCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HengeWalker extends CardImpl {

    public HengeWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Adamant — If at least three mana of the same color was spent to cast this spell, Henge Walker enters the battlefield with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                AdamantCondition.ANY, "<br><i>Adamant</i> &mdash; " +
                "If at least three mana of the same color was spent to cast this spell, " +
                "{this} enters the battlefield with a +1/+1 counter on it.", ""
        ));
    }

    private HengeWalker(final HengeWalker card) {
        super(card);
    }

    @Override
    public HengeWalker copy() {
        return new HengeWalker(this);
    }
}

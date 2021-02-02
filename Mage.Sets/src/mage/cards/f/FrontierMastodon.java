package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FrontierMastodon extends CardImpl {

    public FrontierMastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Ferocious</i> &mdash; Frontier Mastodon enters the battlefield with a +1/+1 counter on it if you control a creature with power 4 or greater.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(1)),
                FerociousCondition.instance,
                "<i>Ferocious</i> &mdash; {this} enters the battlefield with a +1/+1 counter on it if you control a creature with power 4 or greater.", ""
        ).addHint(FerociousHint.instance));
    }

    private FrontierMastodon(final FrontierMastodon card) {
        super(card);
    }

    @Override
    public FrontierMastodon copy() {
        return new FrontierMastodon(this);
    }
}

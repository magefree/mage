package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhastlyGloomhunter extends CardImpl {

    public GhastlyGloomhunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {3}{B}
        this.addAbility(new KickerAbility("{3}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If Ghastly Gloomhunter was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), KickedCondition.instance,
                "If {this} was kicked, it enters the battlefield with two +1/+1 counters on it.", ""
        ));
    }

    private GhastlyGloomhunter(final GhastlyGloomhunter card) {
        super(card);
    }

    @Override
    public GhastlyGloomhunter copy() {
        return new GhastlyGloomhunter(this);
    }
}

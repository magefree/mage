
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class FavoredHoplite extends CardImpl {

    public FavoredHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Heroic - Whenever you cast a spell that targets Favored Hoplite, put a +1/+1 counter on Favored Hoplite and prevent all damage that would be dealt to it this turn.
        Ability ability = new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new PreventAllDamageToSourceEffect(Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private FavoredHoplite(final FavoredHoplite card) {
        super(card);
    }

    @Override
    public FavoredHoplite copy() {
        return new FavoredHoplite(this);
    }
}

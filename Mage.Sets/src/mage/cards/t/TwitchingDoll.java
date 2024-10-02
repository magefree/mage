package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.Spider22Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TwitchingDoll extends CardImpl {

    public TwitchingDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.TOY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add one mana of any color. Put a nest counter on Twitching Doll.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.NEST.createInstance()));
        this.addAbility(ability);

        // {T}, Sacrifice Twitching Doll: Create a 2/2 green Spider creature token with reach for each counter on Twitching Doll. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new Spider22Token(), CountersSourceCount.ANY), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TwitchingDoll(final TwitchingDoll card) {
        super(card);
    }

    @Override
    public TwitchingDoll copy() {
        return new TwitchingDoll(this);
    }
}

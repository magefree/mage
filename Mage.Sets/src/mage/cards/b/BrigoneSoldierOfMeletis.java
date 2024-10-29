package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrigoneSoldierOfMeletis extends CardImpl {

    public BrigoneSoldierOfMeletis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Heroic -- Whenever you cast a spell that targets Brigone, Soldier of Meletis, put a +1/+1 counter on Brigone.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // {T}, Remove a +1/+1 counter from Brigone: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private BrigoneSoldierOfMeletis(final BrigoneSoldierOfMeletis card) {
        super(card);
    }

    @Override
    public BrigoneSoldierOfMeletis copy() {
        return new BrigoneSoldierOfMeletis(this);
    }
}

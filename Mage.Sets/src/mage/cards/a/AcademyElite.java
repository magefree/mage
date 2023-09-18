
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;

/**
 *
 * @author andyfries
 */
public final class AcademyElite extends CardImpl {

    public AcademyElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Academy Elite enters the battlefield with X +1/+1 counters on it, where X is the number of instant and
        // sorcery cards in all graveyards.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(),
                new CardsInAllGraveyardsCount(new FilterInstantOrSorceryCard()), false),
                "with X +1/+1 counters on it, where X is the number of instant and sorcery cards in all graveyards"));

        // {2}{U}, Remove a +1/+1 counter from Academy Elite: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1, false), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        this.addAbility(ability);
    }

    private AcademyElite(final AcademyElite card) {
        super(card);
    }

    @Override
    public AcademyElite copy() {
        return new AcademyElite(this);
    }

}

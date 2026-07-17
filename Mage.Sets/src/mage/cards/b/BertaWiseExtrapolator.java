package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.IncrementAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.FractalToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BertaWiseExtrapolator extends CardImpl {

    public BertaWiseExtrapolator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Increment
        this.addAbility(new IncrementAbility());

        // Whenever one or more +1/+1 counters are put on Berta, add one mana of any color.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(new AddManaOfAnyColorEffect(1)));

        // {X}, {T}: Create a 0/0 green and blue Fractal creature token and put X +1/+1 counters on it.
        Ability ability = new SimpleActivatedAbility(FractalToken.getEffect(
                GetXValue.instance, " and put X +1/+1 counters on it"
        ), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BertaWiseExtrapolator(final BertaWiseExtrapolator card) {
        super(card);
    }

    @Override
    public BertaWiseExtrapolator copy() {
        return new BertaWiseExtrapolator(this);
    }
}

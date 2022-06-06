package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.WhiteElementalToken;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HoofprintsOfTheStag extends CardImpl {

    public HoofprintsOfTheStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        // Whenever you draw a card, you may put a hoofprint counter on Hoofprints of the Stag.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.HOOFPRINT.createInstance(1)), true));

        // {2}{w}, Remove four hoofprint counters from Hoofprints of the Stag: Create a 4/4 white Elemental creature token with flying. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WhiteElementalToken(), 1), new ManaCostsImpl<>("{2}{W}"), MyTurnCondition.instance);
        ability.addCost(new RemoveCountersSourceCost(CounterType.HOOFPRINT.createInstance(4)));
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private HoofprintsOfTheStag(final HoofprintsOfTheStag card) {
        super(card);
    }

    @Override
    public HoofprintsOfTheStag copy() {
        return new HoofprintsOfTheStag(this);
    }
}


package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;

/**
 * @author Loki
 */
public final class WitchMawNephilim extends CardImpl {

    public WitchMawNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{W}{U}{B}");
        this.subtype.add(SubType.NEPHILIM);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell, you may put two +1/+1 counters on Witch-Maw Nephilim.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), true));

        // Whenever Witch-Maw Nephilim attacks, it gains trample until end of turn if its power is 10 or greater.
        this.addAbility(new AttacksTriggeredAbility(new WitchMawNephilimEffect(), false));
    }

    private WitchMawNephilim(final WitchMawNephilim card) {
        super(card);
    }

    @Override
    public WitchMawNephilim copy() {
        return new WitchMawNephilim(this);
    }
}

class WitchMawNephilimEffect extends OneShotEffect {

    public WitchMawNephilimEffect() {
        super(Outcome.AddAbility);
        this.staticText = "it gains trample until end of turn if its power is 10 or greater";
    }

    private WitchMawNephilimEffect(final WitchMawNephilimEffect effect) {
        super(effect);
    }

    @Override
    public WitchMawNephilimEffect copy() {
        return new WitchMawNephilimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            if (sourceObject.getPower().getValue() >= 10) {
                game.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}

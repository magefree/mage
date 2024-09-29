package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayVariableEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author grimreap124
 */
public final class SphinxOfTheRevelation extends CardImpl {

    public SphinxOfTheRevelation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT, CardType.CREATURE }, "{3}{W}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever you gain life, you get that many {E}.
        this.addAbility(new GainLifeControllerTriggeredAbility(new GetEnergyCountersControllerEffect(SavedGainedLifeValue.MANY)));

        // {W}{U}{U}, {T}, Pay X {E}: Draw X cards.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(GetXValue.instance),
                new ManaCostsImpl<>("{W}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new PayVariableEnergyCost());
        this.addAbility(ability);

    }

    private SphinxOfTheRevelation(final SphinxOfTheRevelation card) {
        super(card);
    }

    @Override
    public SphinxOfTheRevelation copy() {
        return new SphinxOfTheRevelation(this);
    }
}
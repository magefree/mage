package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersWithCountersControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ZameckGuildmage extends CardImpl {

    public ZameckGuildmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");
        this.subtype.add(SubType.ELF, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {G}{U}: This turn, each creature you control enters the battlefield with an additional +1/+1 counter on it.
        this.addAbility(new SimpleActivatedAbility(new EntersWithCountersControlledEffect(
                Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE,
                CounterType.P1P1.createInstance(), false
        ), new ManaCostsImpl<>("{G}{U}")));

        // {G}{U}, Remove a +1/+1 counter from a creature you control: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{G}{U}"));
        ability.addCost(new RemoveCounterCost(new TargetControlledCreaturePermanent(), CounterType.P1P1));
        this.addAbility(ability);
    }

    private ZameckGuildmage(final ZameckGuildmage card) {
        super(card);
    }

    @Override
    public ZameckGuildmage copy() {
        return new ZameckGuildmage(this);
    }
}

package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CantLoseGameSourceControllerEffect;
import mage.abilities.effects.common.ruleModifying.CantHaveCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class DarksteelAngel extends CardImpl {

    public DarksteelAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // You can't lose the game and your opponents can't win the game.
        this.addAbility(new SimpleStaticAbility(new CantLoseGameSourceControllerEffect()));

        // Creatures you control can't have -1/-1 counters put on them.
        this.addAbility(new SimpleStaticAbility(new CantHaveCountersAllEffect(
            StaticFilters.FILTER_CONTROLLED_CREATURES, CounterType.M1M1
        )));
    }

    private DarksteelAngel(final DarksteelAngel card) {
        super(card);
    }

    @Override
    public DarksteelAngel copy() {
        return new DarksteelAngel(this);
    }
}

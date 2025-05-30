package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SnowmeltStag extends CardImpl {

    public SnowmeltStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // During your turn, this creature has base power and toughness 5/2.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new SetBasePowerToughnessSourceEffect(5, 2, Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, this creature has base power and toughness 5/2"
        )));

        // {5}{U}{U}: This creature can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{5}{U}{U}")
        ));
    }

    private SnowmeltStag(final SnowmeltStag card) {
        super(card);
    }

    @Override
    public SnowmeltStag copy() {
        return new SnowmeltStag(this);
    }
}

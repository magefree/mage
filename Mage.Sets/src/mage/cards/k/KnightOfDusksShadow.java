package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author weirddan455
 */
public final class KnightOfDusksShadow extends CardImpl {

    public KnightOfDusksShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(new CantGainLifeAllEffect(Duration.WhileOnBattlefield, TargetController.OPPONENT)));

        // {1}{B}: Knight of Dusk's Shadow gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{B}")
        ));
    }

    private KnightOfDusksShadow(final KnightOfDusksShadow card) {
        super(card);
    }

    @Override
    public KnightOfDusksShadow copy() {
        return new KnightOfDusksShadow(this);
    }
}

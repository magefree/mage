package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.replacement.GainPlusOneLifeReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class HeronOfHope extends CardImpl {

    public HeronOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If you would gain life, you gain that much life plus 1 instead.
        this.addAbility(new SimpleStaticAbility(new GainPlusOneLifeReplacementEffect()));

        // {1}{W}: Heron of Hope gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{W}")
        ));
    }

    private HeronOfHope(final HeronOfHope card) {
        super(card);
    }

    @Override
    public HeronOfHope copy() {
        return new HeronOfHope(this);
    }
}

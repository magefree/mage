package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExaltedSunborn extends CardImpl {

    public ExaltedSunborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new SimpleStaticAbility(new CreateTwiceThatManyTokensEffect()));

        // Warp {1}{W}
        this.addAbility(new WarpAbility(this, "{1}{W}"));
    }

    private ExaltedSunborn(final ExaltedSunborn card) {
        super(card);
    }

    @Override
    public ExaltedSunborn copy() {
        return new ExaltedSunborn(this);
    }
}

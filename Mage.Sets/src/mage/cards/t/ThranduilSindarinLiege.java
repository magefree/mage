package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.GreenElfToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ThranduilSindarinLiege extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ELF, "Elves");

    public ThranduilSindarinLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/U}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Elves you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // Landfall -- Whenever a land you control enters, create a 1/1 green Elf creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new GreenElfToken())));
    }

    private ThranduilSindarinLiege(final ThranduilSindarinLiege card) {
        super(card);
    }

    @Override
    public ThranduilSindarinLiege copy() {
        return new ThranduilSindarinLiege(this);
    }
}

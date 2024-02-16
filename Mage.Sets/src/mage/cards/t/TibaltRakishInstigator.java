package mage.cards.t;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.DevilToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TibaltRakishInstigator extends CardImpl {

    public TibaltRakishInstigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIBALT);
        this.setStartingLoyalty(5);

        // Your opponents can't gain life.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantGainLifeAllEffect(
                        Duration.WhileOnBattlefield,
                        TargetController.OPPONENT
                )
        ));

        // -2: Create a 1/1 red Devil creature token with "Whenever this creature dies, it deals 1 damage to any target.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DevilToken()), -2));
    }

    private TibaltRakishInstigator(final TibaltRakishInstigator card) {
        super(card);
    }

    @Override
    public TibaltRakishInstigator copy() {
        return new TibaltRakishInstigator(this);
    }
}

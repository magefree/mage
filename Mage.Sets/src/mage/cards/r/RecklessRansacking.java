package mage.cards.r;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessRansacking extends CardImpl {

    public RecklessRansacking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+2 until end of turn. Create a Treasure token.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken()));
    }

    private RecklessRansacking(final RecklessRansacking card) {
        super(card);
    }

    @Override
    public RecklessRansacking copy() {
        return new RecklessRansacking(this);
    }
}

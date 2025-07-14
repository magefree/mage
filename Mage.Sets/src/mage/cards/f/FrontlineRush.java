package mage.cards.f;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FrontlineRush extends CardImpl {

    public FrontlineRush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Choose one --
        // * Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(), 2));

        // * Target creature gets +X/+X until end of turn, where X is the number of creatures you control.
        this.getSpellAbility().addMode(new Mode(new BoostTargetEffect(
                CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL
        )).addTarget(new TargetCreaturePermanent()));
    }

    private FrontlineRush(final FrontlineRush card) {
        super(card);
    }

    @Override
    public FrontlineRush copy() {
        return new FrontlineRush(this);
    }
}

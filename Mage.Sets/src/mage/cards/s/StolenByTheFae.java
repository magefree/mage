package mage.cards.s;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.FaerieToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StolenByTheFae extends CardImpl {

    public StolenByTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Return target creature with converted mana cost X to its owner's hand. You create X 1/1 blue Faerie creature tokens with flying.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect()
                .setText("Return target creature with mana value X to its owner's hand"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new FaerieToken(), ManacostVariableValue.REGULAR)
                .setText("You create X 1/1 blue Faerie creature tokens with flying"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(new XManaValueTargetAdjuster());
    }

    private StolenByTheFae(final StolenByTheFae card) {
        super(card);
    }

    @Override
    public StolenByTheFae copy() {
        return new StolenByTheFae(this);
    }
}

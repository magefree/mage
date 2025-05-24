package mage.cards.f;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FatedInfatuation extends CardImpl {

    public FatedInfatuation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{U}");


        // Create a token that's a copy of target creature you control. If it's your turn, scry 2.
        this.getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new ScryEffect(2), MyTurnCondition.instance, "If it's your turn, scry 2"));
        this.getSpellAbility().addHint(MyTurnHint.instance);
    }

    private FatedInfatuation(final FatedInfatuation card) {
        super(card);
    }

    @Override
    public FatedInfatuation copy() {
        return new FatedInfatuation(this);
    }
}

package mage.cards.r;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RepudiateReplicate extends SplitCard {

    public RepudiateReplicate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, new CardType[]{CardType.SORCERY}, "{G/U}{G/U}", "{1}{G}{U}", SpellAbilityType.SPLIT);

        // Repudiate
        // Counter target activated ability or triggered ability.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CounterTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetActivatedOrTriggeredAbility());

        // Replicate
        // Create a token that’s a copy of target creature you control.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenCopyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private RepudiateReplicate(final RepudiateReplicate card) {
        super(card);
    }

    @Override
    public RepudiateReplicate copy() {
        return new RepudiateReplicate(this);
    }
}

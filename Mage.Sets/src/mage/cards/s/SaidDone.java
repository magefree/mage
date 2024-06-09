package mage.cards.s;

import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaidDone extends SplitCard {

    public SaidDone(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new CardType[]{CardType.INSTANT},
                "{2}{U}", "{3}{U}", SpellAbilityType.SPLIT
        );

        // Said
        // Return target instant or sorcery card from your graveyard to your hand.
        this.getLeftHalfCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD
        ));

        // Done
        // Tap up to two target creatures. Those creatures don't untap during their controllers' next untap step.
        this.getRightHalfCard().getSpellAbility().addEffect(new TapTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
        this.getRightHalfCard().getSpellAbility().addEffect(new DontUntapInControllersNextUntapStepTargetEffect("Those creatures"));
    }

    private SaidDone(final SaidDone card) {
        super(card);
    }

    @Override
    public SaidDone copy() {
        return new SaidDone(this);
    }
}

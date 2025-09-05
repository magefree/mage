package mage.cards.l;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LethalProtection extends CardImpl {

    public LethalProtection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Destroy target creature. Return up to one target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(
                0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        ));
    }

    private LethalProtection(final LethalProtection card) {
        super(card);
    }

    @Override
    public LethalProtection copy() {
        return new LethalProtection(this);
    }
}

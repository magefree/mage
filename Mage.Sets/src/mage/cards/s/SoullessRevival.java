package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class SoullessRevival extends CardImpl {

    public SoullessRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");
        this.subtype.add(SubType.ARCANE);

        // Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD).withChooseHint("return to hand"));
        // Splice onto Arcane {1}{B}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{B}"));
    }

    private SoullessRevival(final SoullessRevival card) {
        super(card);
    }

    @Override
    public SoullessRevival copy() {
        return new SoullessRevival(this);
    }
}

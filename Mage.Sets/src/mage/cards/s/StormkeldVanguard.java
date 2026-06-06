package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DauntAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormkeldVanguard extends AdventureCard {

    public StormkeldVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GIANT, SubType.WARRIOR}, "{4}{G}{G}",
                "Bear Down",
                new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Stormkeld Vanguard
        this.getLeftHalfCard().setPT(6, 7);

        // Stormkeld Vanguard can't be blocked by creatures with power 2 or less.
        this.getLeftHalfCard().addAbility(new DauntAbility());

        // Bear Down
        // Destroy target artifact or enchantment.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        finalizeCard();
    }

    private StormkeldVanguard(final StormkeldVanguard card) {
        super(card);
    }

    @Override
    public StormkeldVanguard copy() {
        return new StormkeldVanguard(this);
    }
}

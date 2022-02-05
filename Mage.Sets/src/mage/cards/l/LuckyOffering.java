package mage.cards.l;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuckyOffering extends CardImpl {

    private static final FilterPermanent filter
            = new FilterArtifactPermanent("artifact with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public LuckyOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");

        // Destroy target artifact with mana value 3 or less. You gain 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private LuckyOffering(final LuckyOffering card) {
        super(card);
    }

    @Override
    public LuckyOffering copy() {
        return new LuckyOffering(this);
    }
}

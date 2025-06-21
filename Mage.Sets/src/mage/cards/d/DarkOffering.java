package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 *
 * @author fireshoes
 */
public final class DarkOffering extends CardImpl {

    public DarkOffering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");

        // Destroy target nonblack creature. You gain 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private DarkOffering(final DarkOffering card) {
        super(card);
    }

    @Override
    public DarkOffering copy() {
        return new DarkOffering(this);
    }
}

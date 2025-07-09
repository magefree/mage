package mage.cards.b;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class Bramblecrush extends CardImpl {

    public Bramblecrush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}");

        // Destroy target noncreature permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_NON_CREATURE));
    }

    private Bramblecrush(final Bramblecrush card) {
        super(card);
    }

    @Override
    public Bramblecrush copy() {
        return new Bramblecrush(this);
    }
}

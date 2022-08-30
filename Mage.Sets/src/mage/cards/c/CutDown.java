package mage.cards.c;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutDown extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with total power and toughness 5 or less");

    static {
        filter.add(CutDownPredicate.instance);
    }

    public CutDown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Destroy target creature with total power and toughness 5 or less.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private CutDown(final CutDown card) {
        super(card);
    }

    @Override
    public CutDown copy() {
        return new CutDown(this);
    }
}

enum CutDownPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getPower().getValue() + input.getToughness().getValue() <= 5;
    }
}
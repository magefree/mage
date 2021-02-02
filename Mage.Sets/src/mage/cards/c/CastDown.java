package mage.cards.c;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public final class CastDown extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");
    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public CastDown(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[] { CardType.INSTANT }, "{1}{B}");

        // Destroy target nonlegendary creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private CastDown(final CastDown card){
        super(card);
    }

    public CastDown copy(){
        return new CastDown(this);
    }
}
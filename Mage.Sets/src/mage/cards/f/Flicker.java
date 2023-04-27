package mage.cards.f;

import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Flicker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public Flicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Exile target nontoken permanent, then return it to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false, "it").concatBy(", then"));
    }

    private Flicker(final Flicker card) {
        super(card);
    }

    @Override
    public Flicker copy() {
        return new Flicker(this);
    }
}

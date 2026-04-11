package mage.cards.m;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author muz
 */
public final class MomentOfReckoning extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("nonland permanent card");

    static {
        filter.add(PermanentPredicate.instance);
    }

    public MomentOfReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}{B}{B}");

        // Choose up to four. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(0);
        this.getSpellAbility().getModes().setMaxModes(4);
        this.getSpellAbility().getModes().setMayChooseSameModeMoreThanOnce(true);

        // * Destroy target nonland permanent.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // * Return target nonland permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addMode(
            new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(filter))
        );
    }

    private MomentOfReckoning(final MomentOfReckoning card) {
        super(card);
    }

    @Override
    public MomentOfReckoning copy() {
        return new MomentOfReckoning(this);
    }
}

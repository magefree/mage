package mage.cards.s;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SunderingEruption extends ModalDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SunderingEruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.SORCERY}, new SubType[]{}, "{2}{R}",
                "Volcanic Fissure", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Sundering Eruption
        // Sorcery

        // Destroy target land. Its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle. Creatures without flying can't block this turn.
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetLandPermanent());
        this.getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetControllerEffect(true));
        this.getLeftHalfCard().getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));

        // 2.
        // Volcanic Fissure
        // Land

        // As Volcanic Fissure enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());
    }

    private SunderingEruption(final SunderingEruption card) {
        super(card);
    }

    @Override
    public SunderingEruption copy() {
        return new SunderingEruption(this);
    }
}

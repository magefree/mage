package mage.cards.f;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FellTheProfane extends ModalDoubleFacedCard {

    public FellTheProfane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{2}{B}{B}",
                "Fell Mire", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Fell the Profane
        // Instant

        // Destroy target creature or planeswalker.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // 2.
        // Fell Mire
        // Land

        // As Fell Mire enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {B}.
        this.getRightHalfCard().addAbility(new BlackManaAbility());
    }

    private FellTheProfane(final FellTheProfane card) {
        super(card);
    }

    @Override
    public FellTheProfane copy() {
        return new FellTheProfane(this);
    }
}

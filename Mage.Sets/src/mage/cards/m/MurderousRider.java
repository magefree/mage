package mage.cards.m;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MurderousRider extends AdventureCard {

    public MurderousRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.KNIGHT}, "{1}{B}{B}",
                "Swift End",
                new CardType[]{CardType.INSTANT}, "{1}{B}{B}");

        // Murderous Rider
        this.getLeftHalfCard().setPT(2, 3);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // When Murderous Rider dies, put it on the bottom of its owner's library.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new PutOnLibraryTargetEffect(false)
                .setText("put it on the bottom of its owner's library"), false, SetTargetPointer.CARD));

        // Swift End
        // Destroy target creature or planeswalker. You lose 2 life.
        this.getRightHalfCard().getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getRightHalfCard().getSpellAbility().addEffect(
                new LoseLifeSourceControllerEffect(2).setText("You lose 2 life.")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        finalizeCard();
    }

    private MurderousRider(final MurderousRider card) {
        super(card);
    }

    @Override
    public MurderousRider copy() {
        return new MurderousRider(this);
    }
}

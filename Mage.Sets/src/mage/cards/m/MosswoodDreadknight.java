package mage.cards.m;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.asthought.MayCastFromGraveyardAsAdventureEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MosswoodDreadknight extends AdventureCard {


    public MosswoodDreadknight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{G}",
                "Dread Whispers",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Mosswood Dreadknight
        this.getLeftHalfCard().setPT(3, 2);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // When Mosswood Dreadknight dies, you may cast it from your graveyard as an Adventure until the end of your next turn.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new MayCastFromGraveyardAsAdventureEffect()));

        // Dread Whispers
        // You draw a card and you lose 1 life.
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getRightHalfCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        finalizeCard();
    }

    private MosswoodDreadknight(final MosswoodDreadknight card) {
        super(card);
    }

    @Override
    public MosswoodDreadknight copy() {
        return new MosswoodDreadknight(this);
    }
}
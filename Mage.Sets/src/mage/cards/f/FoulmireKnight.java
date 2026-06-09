package mage.cards.f;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoulmireKnight extends AdventureCard {

    public FoulmireKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ZOMBIE, SubType.KNIGHT}, "{B}",
                "Profane Insight",
                new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Foulmire Knight
        this.getLeftHalfCard().setPT(1, 1);

        // Deathtouch
        this.getLeftHalfCard().addAbility(DeathtouchAbility.getInstance());

        // Profane Insight
        // You draw a card and you lose 1 life.
        this.getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1, true));
        this.getRightHalfCard().getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and"));

        finalizeCard();
    }

    private FoulmireKnight(final FoulmireKnight card) {
        super(card);
    }

    @Override
    public FoulmireKnight copy() {
        return new FoulmireKnight(this);
    }
}

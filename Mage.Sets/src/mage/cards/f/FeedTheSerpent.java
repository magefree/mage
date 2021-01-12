package mage.cards.f;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FeedTheSerpent extends CardImpl {

    public FeedTheSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Exile target creature or planeswalker.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private FeedTheSerpent(final FeedTheSerpent card) {
        super(card);
    }

    @Override
    public FeedTheSerpent copy() {
        return new FeedTheSerpent(this);
    }
}

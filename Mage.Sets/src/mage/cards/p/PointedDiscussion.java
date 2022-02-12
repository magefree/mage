package mage.cards.p;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PointedDiscussion extends CardImpl {

    public PointedDiscussion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw two cards, lose 2 life, then create a Blood token.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText("you draw two cards"));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).setText(", lose 2 life"));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BloodToken()).concatBy(", then"));
    }

    private PointedDiscussion(final PointedDiscussion card) {
        super(card);
    }

    @Override
    public PointedDiscussion copy() {
        return new PointedDiscussion(this);
    }
}

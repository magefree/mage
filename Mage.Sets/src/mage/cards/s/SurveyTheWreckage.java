
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetLandPermanent;

/**
 * @author LevelX2
 */
public final class SurveyTheWreckage extends CardImpl {

    public SurveyTheWreckage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{R}");


        // Destroy target land. Create a 1/1 red Goblin creature token.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken()));
    }

    private SurveyTheWreckage(final SurveyTheWreckage card) {
        super(card);
    }

    @Override
    public SurveyTheWreckage copy() {
        return new SurveyTheWreckage(this);
    }
}
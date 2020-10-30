
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DrownInFilth extends CardImpl {

    public DrownInFilth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{G}");


        // Choose target creature. Put the top four cards of your library into your graveyard, then that creature gets -1/-1 until end of turn for each land card in your graveyard.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new MillCardsControllerEffect(4);
        effect.setText("Choose target creature. Mill four cards");
        this.getSpellAbility().addEffect(effect);
        DynamicValue landCards = new SignInversionDynamicValue(new CardsInControllerGraveyardCount(new FilterLandCard()));
        this.getSpellAbility().addEffect(new BoostTargetEffect(landCards, landCards, Duration.EndOfTurn));
    }

    public DrownInFilth(final DrownInFilth card) {
        super(card);
    }

    @Override
    public DrownInFilth copy() {
        return new DrownInFilth(this);
    }
}


package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author andyfries
 */
public final class PoliticalTrickery extends CardImpl {

    private static final String rule = "exchange control of target land you control and target land an opponent controls";

    private static final FilterLandPermanent filter = new FilterLandPermanent("land an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PoliticalTrickery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // Exchange control of target land you control and target land an opponent controls.
        Effect effect = new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true);
        effect.setText("exchange control of target land you control and target land an opponent controls");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private PoliticalTrickery(final PoliticalTrickery card) {
        super(card);
    }

    @Override
    public PoliticalTrickery copy() {
        return new PoliticalTrickery(this);
    }
}

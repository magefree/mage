package mage.cards.b;

import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetActivatedOrTriggeredAbility;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrokersConfluence extends CardImpl {

    public BrokersConfluence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}{W}{U}");

        // Choose three. You may choose the same mode more than once.
        this.getSpellAbility().getModes().setMinModes(3);
        this.getSpellAbility().getModes().setMaxModes(3);
        this.getSpellAbility().getModes().setEachModeMoreThanOnce(true);

        // • Proliferate.
        this.getSpellAbility().addEffect(new ProliferateEffect());

        // • Target creature phases out.
        this.getSpellAbility().addMode(new Mode(new PhaseOutTargetEffect()).addTarget(new TargetCreaturePermanent()));

        // • Counter target activated or triggered ability.
        this.getSpellAbility().addMode(new Mode(new CounterTargetEffect()).addTarget(new TargetActivatedOrTriggeredAbility()));
    }

    private BrokersConfluence(final BrokersConfluence card) {
        super(card);
    }

    @Override
    public BrokersConfluence copy() {
        return new BrokersConfluence(this);
    }
}

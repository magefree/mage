package mage.cards.i;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InevitableDefeat extends CardImpl {

    public InevitableDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}{B}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Exile target nonland permanent. Its controller loses 3 life and you gain 3 life.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3));
        this.getSpellAbility().addEffect(new GainLifeEffect(3).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private InevitableDefeat(final InevitableDefeat card) {
        super(card);
    }

    @Override
    public InevitableDefeat copy() {
        return new InevitableDefeat(this);
    }
}

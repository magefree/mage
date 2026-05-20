package mage.cards.d;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.SecondTargetPointer;
import mage.target.targetpointer.ThirdTargetPointer;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DissectionPractice extends CardImpl {

    public DissectionPractice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target opponent loses 1 life and you gain 1 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Up to one target creature gets +1/+1 until end of turn.
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(1, 1)
                        .setTargetPointer(new SecondTargetPointer())
                        .concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1).withChooseHint("to get +1/+1"));

        // Up to one target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(
                new BoostTargetEffect(-1, -1)
                        .setTargetPointer(new ThirdTargetPointer())
                        .concatBy("<br>"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1).withChooseHint("to get -1/-1"));
    }

    private DissectionPractice(final DissectionPractice card) {
        super(card);
    }

    @Override
    public DissectionPractice copy() {
        return new DissectionPractice(this);
    }
}

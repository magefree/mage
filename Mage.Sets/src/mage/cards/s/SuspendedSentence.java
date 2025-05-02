package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author padfoothelix 
 */
public final class SuspendedSentence extends CardImpl {

    public SuspendedSentence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");
        

        // Destroy target creature an opponent controls. That player loses 3 life. Exile Suspended Sentence with three time counters on it.
	this.getSpellAbility().addEffect(new DestroyTargetEffect());
	this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(3).setText("That player loses 3 life"));
	this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
	this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));

        // Suspend 3--{1}{B}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{B}"), this));

    }

    private SuspendedSentence(final SuspendedSentence card) {
        super(card);
    }

    @Override
    public SuspendedSentence copy() {
        return new SuspendedSentence(this);
    }
}

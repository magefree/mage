package mage.cards.s;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Schismotivate extends CardImpl {

    public Schismotivate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{R}");

        // Target creature gets +4/+0 until end of turn. Another target creature gets -4/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4, 0));
        this.getSpellAbility().addEffect(new BoostTargetEffect(-4, 0)
                .setTargetPointer(new SecondTargetPointer())
                .setText("Another target creature gets -4/-0 until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("+4/+0").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("-4/-0").setTargetTag(2));
    }

    private Schismotivate(final Schismotivate card) {
        super(card);
    }

    @Override
    public Schismotivate copy() {
        return new Schismotivate(this);
    }
}

package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class CodeSlice extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Artifact creatures with bounty counters on them");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public CodeSlice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");
        

        // Put a bounty counter on target creature.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Artifact creatures with bounty counters on them can't block this turn.
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn));
    }

    private CodeSlice(final CodeSlice card) {
        super(card);
    }

    @Override
    public CodeSlice copy() {
        return new CodeSlice(this);
    }
}

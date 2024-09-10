package mage.cards.d;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

public class DyadForceTransfer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature permanent");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DyadForceTransfer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        //Gain control of target noncreature permanent until end of turn. Untap it.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap it"));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        //Scry 3.
        this.getSpellAbility().addEffect(new ScryEffect(3, true));
    }

    private DyadForceTransfer(final DyadForceTransfer card) {
        super(card);
    }

    @Override
    public DyadForceTransfer copy() {
        return new DyadForceTransfer(this);
    }
}

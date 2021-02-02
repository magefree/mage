
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class ShrewdNegotiation extends CardImpl {

    private static final String rule = "Exchange control of target artifact you control and target artifact or creature you don't control";

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public ShrewdNegotiation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");

        // Exchange control of target artifact you control and target artifact or creature you don't control.
        getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, rule, false, true));
        getSpellAbility().addTarget(new TargetControlledPermanent(new FilterControlledArtifactPermanent("artifact you control")));
        getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ShrewdNegotiation(final ShrewdNegotiation card) {
        super(card);
    }

    @Override
    public ShrewdNegotiation copy() {
        return new ShrewdNegotiation(this);
    }
}

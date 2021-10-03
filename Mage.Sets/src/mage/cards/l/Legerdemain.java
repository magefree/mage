
package mage.cards.l;

import java.util.UUID;
import mage.MageItem;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class Legerdemain extends CardImpl {
    
    private static final FilterPermanent firstFilter = new FilterPermanent("artifact or creature");
    private static final FilterPermanent secondFilter = new FilterPermanent("another permanent that shares the type of artifact or creature");
    static {
        firstFilter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
        secondFilter.add(new AnotherTargetPredicate(2));
        secondFilter.add(new SharesTypePredicate());
    }

    public Legerdemain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Exchange control of target artifact or creature and another target permanent that shares one of those types with it.
        this.getSpellAbility().addEffect(new ExchangeControlTargetEffect(Duration.EndOfGame, "Exchange control of target artifact or creature and another target permanent that shares one of those types with it", false, true));
        TargetPermanent firstTarget = new TargetPermanent(firstFilter);
        firstTarget.setTargetTag(1);
        TargetPermanent secondTarget = new TargetPermanent(secondFilter);
        secondTarget.setTargetTag(2);
        this.getSpellAbility().addTarget(firstTarget);
        this.getSpellAbility().addTarget(secondTarget);
    }

    private Legerdemain(final Legerdemain card) {
        super(card);
    }

    @Override
    public Legerdemain copy() {
        return new Legerdemain(this);
    }
}

class SharesTypePredicate implements ObjectSourcePlayerPredicate<MageItem> {

    @Override
    public boolean apply(ObjectSourcePlayer<MageItem> input, Game game) {
        StackObject source = game.getStack().getStackObject(input.getSourceId());
        if (source != null) {
            if (source.getStackAbility().getTargets().isEmpty()
                || source.getStackAbility().getTargets().get(0).getTargets().isEmpty()) {
                return true;
            }
            Permanent firstPermanent = game.getPermanent(
                    source.getStackAbility().getTargets().get(0).getTargets().get(0));
            Permanent secondPermanent = game.getPermanent(input.getObject().getId());
            if (firstPermanent != null && secondPermanent != null) {
                if (firstPermanent.isCreature(game) && secondPermanent.isCreature(game)) {
                    return true;
                }
                if (firstPermanent.isArtifact(game) && secondPermanent.isArtifact(game)) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Target permanent that shares the type of artifact or creature";
    }
    
}
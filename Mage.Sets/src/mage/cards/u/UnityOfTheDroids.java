
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class UnityOfTheDroids extends CardImpl {

    private static final FilterCreaturePermanent artifactCreatureFilter = new FilterCreaturePermanent("artifact creature");
    private static final FilterCreaturePermanent nonArtifactCreatureFilter = new FilterCreaturePermanent("nonartifact creature");

    static {
        artifactCreatureFilter.add(new CardTypePredicate(CardType.ARTIFACT));
        nonArtifactCreatureFilter.add(Predicates.not(new CardTypePredicate(CardType.ARTIFACT)));

    }

    public UnityOfTheDroids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{B}");

        // Choose one - Prevent all damage that would be dealt to target artifact creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(artifactCreatureFilter));

        //   Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        Mode mode = new Mode();
        mode.getEffects().add(new LookLibraryAndPickControllerEffect(new StaticValue(4), false, new StaticValue(1), new FilterCard(), Zone.GRAVEYARD, false, false));
        this.getSpellAbility().addMode(mode);

        //   Destroy target nonartifact creature.
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetCreaturePermanent(nonArtifactCreatureFilter));
        this.getSpellAbility().addMode(mode);
    }

    public UnityOfTheDroids(final UnityOfTheDroids card) {
        super(card);
    }

    @Override
    public UnityOfTheDroids copy() {
        return new UnityOfTheDroids(this);
    }
}

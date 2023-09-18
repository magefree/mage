package mage.cards.u;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class UnityOfTheDroids extends CardImpl {

    private static final FilterCreaturePermanent artifactCreatureFilter = new FilterCreaturePermanent("artifact creature");
    private static final FilterCreaturePermanent nonArtifactCreatureFilter = new FilterCreaturePermanent("nonartifact creature");

    static {
        artifactCreatureFilter.add(CardType.ARTIFACT.getPredicate());
        nonArtifactCreatureFilter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));

    }

    public UnityOfTheDroids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}{B}");

        // Choose one - Prevent all damage that would be dealt to target artifact creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(artifactCreatureFilter));

        //   Look at the top four cards of your library. Put one of them into your hand and the rest into your graveyard.
        Mode mode = new Mode(new LookLibraryAndPickControllerEffect(4, 1, PutCards.HAND, PutCards.GRAVEYARD));
        this.getSpellAbility().addMode(mode);

        //   Destroy target nonartifact creature.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(nonArtifactCreatureFilter));
        this.getSpellAbility().addMode(mode);
    }

    private UnityOfTheDroids(final UnityOfTheDroids card) {
        super(card);
    }

    @Override
    public UnityOfTheDroids copy() {
        return new UnityOfTheDroids(this);
    }
}

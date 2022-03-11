package mage.cards.v;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class VadersCommand extends CardImpl {

    private static final FilterPermanent filterPlaneswalker = new FilterPermanent("planeswalker");
    private static final FilterCreaturePermanent filterNonArtifact = new FilterCreaturePermanent("nonartifact creature");

    static {
        filterPlaneswalker.add(CardType.PLANESWALKER.getPredicate());
        filterNonArtifact.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
    }

    public VadersCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");
        

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        //   Counter target spell unless its controller pays 5 life.
        this.getSpellAbility().addEffect(new CounterUnlessPaysEffect(new PayLifeCost(5))
                .setText("Counter target spell unless its controller pays 5 life"));
        this.getSpellAbility().addTarget(new TargetSpell());

        //   Destroy target planeswalker.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterPlaneswalker));
        this.getSpellAbility().addMode(mode);

        //   Destroy target nonartifact creature.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetCreaturePermanent(filterNonArtifact));
        this.getSpellAbility().addMode(mode);

        //   Gain 5 life.
        mode = new Mode(new GainLifeEffect(5).setText("Gain 5 life"));
        this.getSpellAbility().addMode(mode);
    }

    private VadersCommand(final VadersCommand card) {
        super(card);
    }

    @Override
    public VadersCommand copy() {
        return new VadersCommand(this);
    }
}

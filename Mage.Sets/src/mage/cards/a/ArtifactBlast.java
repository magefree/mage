package mage.cards.a;

import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 *
 * @author Jgod
 */
public final class ArtifactBlast extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("artifact spell");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ArtifactBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Counter target artifact spell.
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        this.getSpellAbility().addEffect(new CounterTargetEffect());
    }

    private ArtifactBlast(final ArtifactBlast card) {
        super(card);
    }

    @Override
    public ArtifactBlast copy() {
        return new ArtifactBlast(this);
    }
}

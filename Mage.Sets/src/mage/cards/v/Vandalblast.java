package mage.cards.v;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Vandalblast extends CardImpl {

    private static final FilterArtifactPermanent FILTER = new FilterArtifactPermanent("artifact you don't control");

    static {
        FILTER.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public Vandalblast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Destroy target artifact you don't control.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent(FILTER));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());

        // Overload {4}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        this.addAbility(new OverloadAbility(this, new DestroyAllEffect(FILTER), new ManaCostsImpl<>("{4}{R}")));

    }

    private Vandalblast(final Vandalblast card) {
        super(card);
    }

    @Override
    public Vandalblast copy() {
        return new Vandalblast(this);
    }
}

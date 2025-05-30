
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Touchstone extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public Touchstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {tap}: Tap target artifact you don't control.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Touchstone(final Touchstone card) {
        super(card);
    }

    @Override
    public Touchstone copy() {
        return new Touchstone(this);
    }
}

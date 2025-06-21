
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Plopman
 */
public final class Karakas extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public Karakas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
        // {T}: Return target legendary creature to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private Karakas(final Karakas card) {
        super(card);
    }

    @Override
    public Karakas copy() {
        return new Karakas(this);
    }
}

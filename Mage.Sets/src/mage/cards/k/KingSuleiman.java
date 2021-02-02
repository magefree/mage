package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author KholdFuzion
 */
public final class KingSuleiman extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Djinn or Efreet");

    static {
        filter.add(Predicates.or(
                SubType.DJINN.getPredicate(),
                SubType.EFREET.getPredicate()
        ));
    }

    public KingSuleiman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN, SubType.NOBLE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target Djinn or Efreet.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KingSuleiman(final KingSuleiman card) {
        super(card);
    }

    @Override
    public KingSuleiman copy() {
        return new KingSuleiman(this);
    }
}

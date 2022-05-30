package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatriarsSeal extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("legendary creature you control");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public PatriarsSeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}, {T}: Untap target legendary creature you control.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private PatriarsSeal(final PatriarsSeal card) {
        super(card);
    }

    @Override
    public PatriarsSeal copy() {
        return new PatriarsSeal(this);
    }
}

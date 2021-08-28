package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PrismToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class DiamondKaleidoscope extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Prism token");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(SubType.PRISM.getPredicate());
    }

    public DiamondKaleidoscope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Create a 0/1 colorless Prism artifact creature token.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new PrismToken(), 1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Sacrifice a Prism token: Add one mana of any color.
        ability = new AnyColorManaAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter)),
                new PermanentsOnBattlefieldCount(filter),
                false);
        this.addAbility(ability);
    }

    private DiamondKaleidoscope(final DiamondKaleidoscope card) {
        super(card);
    }

    @Override
    public DiamondKaleidoscope copy() {
        return new DiamondKaleidoscope(this);
    }
}

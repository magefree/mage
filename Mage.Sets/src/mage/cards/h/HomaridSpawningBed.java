
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SacrificeCostConvertedMana;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.CamaridToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class HomaridSpawningBed extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public HomaridSpawningBed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // {1}{U}{U}, Sacrifice a blue creature: create X 1/1 blue Camarid creature tokens, where X is the sacrificed creature's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new CamaridToken(), new SacrificeCostConvertedMana("creature")),
                new ManaCostsImpl<>("{1}{U}{U}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(filter)));
        this.addAbility(ability);
    }

    private HomaridSpawningBed(final HomaridSpawningBed card) {
        super(card);
    }

    @Override
    public HomaridSpawningBed copy() {
        return new HomaridSpawningBed(this);
    }
}

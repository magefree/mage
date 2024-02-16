
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromTopOfLibraryCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author L_J
 */
public final class WhirlingCatapult extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public WhirlingCatapult(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {2}, Exile the top two cards of your library: Whirling Catapult deals 1 damage to each creature with flying and each player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageEverythingEffect(1, filter), new ManaCostsImpl<>("{2}"));
        ability.addCost(new ExileFromTopOfLibraryCost(2));
        this.addAbility(ability);
    }

    private WhirlingCatapult(final WhirlingCatapult card) {
        super(card);
    }

    @Override
    public WhirlingCatapult copy() {
        return new WhirlingCatapult(this);
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllControlledTargetEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class AjaniVengeant extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("lands");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public AjaniVengeant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{2}{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(3);

        // +1: Target permanent doesn't untap during its controller's next untap step.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DontUntapInControllersNextUntapStepTargetEffect(), 1);
        ability1.addTarget(new TargetPermanent());
        this.addAbility(ability1);

        // −2: Ajani Vengeant deals 3 damage to any target and you gain 3 life.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(3));
        effects1.add(new GainLifeEffect(3).concatBy("and"));
        LoyaltyAbility ability2 = new LoyaltyAbility(effects1, -2);
        ability2.addTarget(new TargetAnyTarget());
        this.addAbility(ability2);

        // −7: Destroy all lands target player controls.
        LoyaltyAbility ability3 = new LoyaltyAbility(new DestroyAllControlledTargetEffect(filter), -7);
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);

    }

    private AjaniVengeant(final AjaniVengeant card) {
        super(card);
    }

    @Override
    public AjaniVengeant copy() {
        return new AjaniVengeant(this);
    }
}

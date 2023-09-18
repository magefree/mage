
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.Effects;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author TheElk801
 */
public final class ChandraBoldPyromancer extends CardImpl {

    public ChandraBoldPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(5);

        // +1: Add {R}{R}. Chandra, Bold Pyromancer deals 2 damage to target player.
        Ability ability = new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), +1);
        ability.addEffect(new DamageTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // −3: Chandra, Bold Pyromancer deals 3 damage to target creature or planeswalker.
        ability = new LoyaltyAbility(new DamageTargetEffect(3), -3);
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // −7: Chandra, Bold Pyromancer deals 10 damage to target player and each creature and planeswalker they control.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(10));
        effects1.add(new DamageAllControlledTargetEffect(10, new FilterCreatureOrPlaneswalkerPermanent()).setText("and each creature and planeswalker they control"));
        LoyaltyAbility ability3 = new LoyaltyAbility(effects1, -7);
        ability3.addTarget(new TargetPlayer());
        this.addAbility(ability3);
    }

    private ChandraBoldPyromancer(final ChandraBoldPyromancer card) {
        super(card);
    }

    @Override
    public ChandraBoldPyromancer copy() {
        return new ChandraBoldPyromancer(this);
    }
}

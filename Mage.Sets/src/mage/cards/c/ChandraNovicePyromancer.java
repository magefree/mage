package mage.cards.c;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraNovicePyromancer extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.ELEMENTAL, "Elementals");

    public ChandraNovicePyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(5);

        // +1: Elementals you control get +2/+0 until end of turn.
        this.addAbility(new LoyaltyAbility(
                new BoostControlledEffect(2, 0, Duration.EndOfTurn, filter), 1
        ));

        // -1: Add {R}{R}.
        this.addAbility(new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(2)), -1));

        // -2: Chandra, Novice Pyromancer deals 2 damage to any target.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ChandraNovicePyromancer(final ChandraNovicePyromancer card) {
        super(card);
    }

    @Override
    public ChandraNovicePyromancer copy() {
        return new ChandraNovicePyromancer(this);
    }
}

package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DamageTargetAndAllControlledEffect;
import mage.abilities.effects.common.DamageTargetAndTargetControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChandraFlamesFury extends CardImpl {

    public ChandraFlamesFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);
        this.setStartingLoyalty(4);

        // +1: Chandra, Flame's Fury deals 2 damage to any target.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // −2: Chandra, Flame's Fury deals 4 damage to target creature and 2 damage to that creature's controller.
        ability = new LoyaltyAbility(new DamageTargetAndTargetControllerEffect(4, 2), -2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −8: Chandra, Flame's Fury deals 10 damage to target player and each creature that player controls.
        ability = new LoyaltyAbility(new DamageTargetAndAllControlledEffect(10), -8);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private ChandraFlamesFury(final ChandraFlamesFury card) {
        super(card);
    }

    @Override
    public ChandraFlamesFury copy() {
        return new ChandraFlamesFury(this);
    }
}

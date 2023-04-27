package mage.cards.c;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ChandraTheFirebrand extends CardImpl {

    public ChandraTheFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(3);

        // +1: Chandra, the Firebrand deals 1 damage to any target.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(1), 1);
        ability1.addTarget(new TargetAnyTarget());
        this.addAbility(ability1);

        // -2: When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.addAbility(new LoyaltyAbility(
                new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()), -2
        ));

        // -6: Chandra, the Firebrand deals 6 damage to each of up to six target creatures and/or players
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(6, true, "each of up to six targets"), -6);
        ability2.addTarget(new TargetAnyTarget(0, 6));
        this.addAbility(ability2);
    }

    private ChandraTheFirebrand(final ChandraTheFirebrand card) {
        super(card);
    }

    @Override
    public ChandraTheFirebrand copy() {
        return new ChandraTheFirebrand(this);
    }
}

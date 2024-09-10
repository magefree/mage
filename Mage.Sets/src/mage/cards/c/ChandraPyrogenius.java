package mage.cards.c;

import java.util.UUID;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class ChandraPyrogenius extends CardImpl {

    public ChandraPyrogenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(5);

        // +2: Chandra, Pyrogenius deals 2 damage to each opponent.
        this.addAbility(new LoyaltyAbility(new DamagePlayersEffect(Outcome.Damage, StaticValue.get(2), TargetController.OPPONENT), 2));

        // -3: Chandra, Pyrogenius deals 4 damage to target creature.
        LoyaltyAbility ability = new LoyaltyAbility(new DamageTargetEffect(4), -3);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // -10: Chandra, Pyrogenius deals 6 damage to target player and each creature they control.
        ability = new LoyaltyAbility(new DamageTargetEffect(6), -10);
        ability.addEffect(new DamageAllControlledTargetEffect(6)
                .setText("and each creature that player or that planeswalker's controller controls"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private ChandraPyrogenius(final ChandraPyrogenius card) {
        super(card);
    }

    @Override
    public ChandraPyrogenius copy() {
        return new ChandraPyrogenius(this);
    }
}

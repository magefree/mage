
package mage.cards.c;

import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GetXLoyaltyValue;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public final class ChandraNalaar extends CardImpl {

    public ChandraNalaar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(6);

        // +1: Chandra Nalaar deals 1 damage to target player or planeswalker.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DamageTargetEffect(1), 1);
        ability1.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability1);

        // -X: Chandra Nalaar deals X damage to target creature.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(GetXLoyaltyValue.instance));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);

        // -8: Chandra Nalaar deals 10 damage to target player or planeswalker and each creature that player or that planeswalker’s controller controls.
        Effects effects1 = new Effects();
        effects1.add(new DamageTargetEffect(10));
        effects1.add(new DamageAllControlledTargetEffect(10, new FilterCreaturePermanent())
                .setText("and each creature that player or that planeswalker's controller controls")
        );
        LoyaltyAbility ability3 = new LoyaltyAbility(effects1, -8);
        ability3.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability3);
    }

    private ChandraNalaar(final ChandraNalaar card) {
        super(card);
    }

    @Override
    public ChandraNalaar copy() {
        return new ChandraNalaar(this);
    }
}


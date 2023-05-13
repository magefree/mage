package mage.cards.r;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class ReySkywalker extends CardImpl {
    public ReySkywalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.addSubType(SubType.REY);

        this.setStartingLoyalty(2);

        //+2: Untap target permanent. Scry 1.
        LoyaltyAbility loyaltyAbilityP2 = new LoyaltyAbility(new UntapTargetEffect(), +2);
        loyaltyAbilityP2.addTarget(new TargetPermanent());
        loyaltyAbilityP2.addEffect(new ScryEffect(1));
        this.addAbility(loyaltyAbilityP2);

        //+1: Tap target creature. That creature doesn't untap during controller's next untap step. Scry 1.
        LoyaltyAbility loyaltyAbilityP1 = new LoyaltyAbility(new TapTargetEffect(), +1);
        loyaltyAbilityP1.addEffect(new DontUntapInControllersNextUntapStepTargetEffect());
        loyaltyAbilityP1.addTarget(new TargetCreaturePermanent());
        loyaltyAbilityP1.addEffect(new ScryEffect(1));
        this.addAbility(loyaltyAbilityP1);

        //0: Draw a card. Scry 1.
        LoyaltyAbility loyaltyAbility0 = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 0);
        loyaltyAbility0.addEffect(new ScryEffect(1));
        this.addAbility(loyaltyAbility0);
    }

    public ReySkywalker(final ReySkywalker card) {
        super(card);
    }

    @Override
    public ReySkywalker copy() {
        return new ReySkywalker(this);
    }
}

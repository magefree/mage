package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerchantRaiders extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PIRATE, "Pirate");

    public MerchantRaiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Merchant Raiders or another Pirate enters the battlefield under your control, tap up to one target creature. That creature doesn't untap during its controller's untap step for as long as you control Merchant Raiders.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new TapTargetEffect("tap up to one target creature"),
                filter, false, true
        );
        ability.addEffect(new DontUntapInControllersUntapStepTargetEffect(Duration.WhileControlled));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private MerchantRaiders(final MerchantRaiders card) {
        super(card);
    }

    @Override
    public MerchantRaiders copy() {
        return new MerchantRaiders(this);
    }
}

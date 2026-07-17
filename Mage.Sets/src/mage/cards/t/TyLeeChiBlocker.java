package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyLeeChiBlocker extends CardImpl {

    public TyLeeChiBlocker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PERFORMER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // When Ty Lee enters, tap up to one target creature. It doesn't untap during its controller's untap step for as long as you control Ty Lee.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new DontUntapInControllersUntapStepTargetEffect(Duration.WhileControlled, "it"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private TyLeeChiBlocker(final TyLeeChiBlocker card) {
        super(card);
    }

    @Override
    public TyLeeChiBlocker copy() {
        return new TyLeeChiBlocker(this);
    }
}

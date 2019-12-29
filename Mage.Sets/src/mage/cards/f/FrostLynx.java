package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FrostLynx extends CardImpl {

    public FrostLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Frost Lynx enters the battlefield, tap target creature an opponent controls.  It doesn't untap during its controller's next untap step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DontUntapInControllersNextUntapStepTargetEffect("That creature"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    public FrostLynx(final FrostLynx card) {
        super(card);
    }

    @Override
    public FrostLynx copy() {
        return new FrostLynx(this);
    }
}

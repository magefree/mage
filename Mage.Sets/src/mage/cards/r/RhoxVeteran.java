package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RhoxVeteran extends CardImpl {

    public RhoxVeteran(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // Whenever Rhox Veteran attacks, tap target creature an opponent controls.
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect(), false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private RhoxVeteran(final RhoxVeteran card) {
        super(card);
    }

    @Override
    public RhoxVeteran copy() {
        return new RhoxVeteran(this);
    }
}

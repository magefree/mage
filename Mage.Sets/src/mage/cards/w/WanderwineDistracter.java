package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WanderwineDistracter extends CardImpl {

    public WanderwineDistracter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever this creature becomes tapped, target creature an opponent controls gets -3/-0 until end of turn.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new BoostTargetEffect(-3, 0));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private WanderwineDistracter(final WanderwineDistracter card) {
        super(card);
    }

    @Override
    public WanderwineDistracter copy() {
        return new WanderwineDistracter(this);
    }
}

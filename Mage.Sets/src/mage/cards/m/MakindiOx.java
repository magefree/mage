package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakindiOx extends CardImpl {

    public MakindiOx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.OX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Landfall - Whenever a land enters the battlefield under your control, tap target creature an opponent controls.
        Ability ability = new LandfallAbility(new TapTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private MakindiOx(final MakindiOx card) {
        super(card);
    }

    @Override
    public MakindiOx copy() {
        return new MakindiOx(this);
    }
}

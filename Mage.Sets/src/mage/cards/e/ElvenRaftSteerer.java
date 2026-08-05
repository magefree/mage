package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ElvenRaftSteerer extends CardImpl {

    public ElvenRaftSteerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, choose one --
        // * Tap target creature an opponent controls.
        Ability ability = new LandfallAbility(new TapTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));

        // * Untap target creature you control.
        Mode mode = new Mode(new UntapTargetEffect());
        mode.addTarget(new TargetControlledCreaturePermanent());
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private ElvenRaftSteerer(final ElvenRaftSteerer card) {
        super(card);
    }

    @Override
    public ElvenRaftSteerer copy() {
        return new ElvenRaftSteerer(this);
    }
}

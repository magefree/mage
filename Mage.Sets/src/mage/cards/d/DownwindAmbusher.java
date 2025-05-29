package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DownwindAmbusher extends CardImpl {

    public DownwindAmbusher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKUNK);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Downwind Ambusher enters, choose one --
        // * Target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-1, -1));
        ability.addTarget(new TargetOpponentsCreaturePermanent());

        // * Destroy target creature an opponent controls that was dealt damage this turn.
        ability.addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_CREATURE_DAMAGED_THIS_TURN)));
        this.addAbility(ability);
    }

    private DownwindAmbusher(final DownwindAmbusher card) {
        super(card);
    }

    @Override
    public DownwindAmbusher copy() {
        return new DownwindAmbusher(this);
    }
}

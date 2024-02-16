package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class TempestCaller extends CardImpl {

    public TempestCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Tempest Caller enters the battlefield, tap all creatures target opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapAllTargetPlayerControlsEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TempestCaller(final TempestCaller card) {
        super(card);
    }

    @Override
    public TempestCaller copy() {
        return new TempestCaller(this);
    }
}

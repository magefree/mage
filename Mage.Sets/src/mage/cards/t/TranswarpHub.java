package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class TranswarpHub extends CardImpl {

    public TranswarpHub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.SPACECRAFT);

        // When this Spacecraft enters, mill five cards. You may put up to two artifact cards from among them into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillThenPutInHandEffect(
                5, StaticFilters.FILTER_CARD_ARTIFACTS, null, true, 2
        ).withTextOptions("them")));

        // Station
        this.addAbility(new StationAbility());

        // STATION 3+
        // {T}: Target creature can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new CantBeBlockedTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new StationLevelAbility(3).withLevelAbility(ability));
    }

    private TranswarpHub(final TranswarpHub card) {
        super(card);
    }

    @Override
    public TranswarpHub copy() {
        return new TranswarpHub(this);
    }
}

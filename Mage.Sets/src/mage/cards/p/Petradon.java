package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class Petradon extends CardImpl {

    public Petradon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Petradon enters the battlefield, exile two target lands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetForSourceEffect(), false);
        ability.addTarget(new TargetLandPermanent(2, 2, StaticFilters.FILTER_LANDS, false));
        this.addAbility(ability);

        // When Petradon leaves the battlefield, return the exiled cards to the battlefield under their owners' control.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD)
                .withReturnName("cards", "their owners'"), false));

        // {R}: Petradon gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    private Petradon(final Petradon card) {
        super(card);
    }

    @Override
    public Petradon copy() {
        return new Petradon(this);
    }
}

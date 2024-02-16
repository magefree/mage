package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class GnarlrootPallbearer extends CardImpl {

    private static final CardsInControllerGraveyardCount count =
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES, null);

    public GnarlrootPallbearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Gnarlroot Pallbearer enters the battlefield, target creature gets +X/+X until end of turn, where X is the number of creature cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(count, count, Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GnarlrootPallbearer(final GnarlrootPallbearer card) {
        super(card);
    }

    @Override
    public GnarlrootPallbearer copy() {
        return new GnarlrootPallbearer(this);
    }
}

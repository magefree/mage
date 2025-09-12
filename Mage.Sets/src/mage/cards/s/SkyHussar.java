package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SkyHussar extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("untapped white and/or blue creatures you control");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.WHITE),
                new ColorPredicate(ObjectColor.BLUE)
        ));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SkyHussar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Sky Hussar enters the battlefield, untap all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllControllerEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURES, "untap all creatures you control"
        ), false));

        // Forecast - Tap two untapped white and/or blue creatures you control, Reveal Sky Hussar from your hand: Draw a card.
        this.addAbility(new ForecastAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(2, filter)
        ));
    }

    private SkyHussar(final SkyHussar card) {
        super(card);
    }

    @Override
    public SkyHussar copy() {
        return new SkyHussar(this);
    }
}

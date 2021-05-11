
package mage.cards.s;

import java.util.UUID;
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
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class SkyHussar extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white and/or blue creatures you control");
    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.WHITE), new ColorPredicate(ObjectColor.BLUE)));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SkyHussar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Sky Hussar enters the battlefield, untap all creatures you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap all creatures you control"), false));

        // Forecast - Tap two untapped white and/or blue creatures you control, Reveal Sky Hussar from your hand: Draw a card.
        this.addAbility(new ForecastAbility(new DrawCardSourceControllerEffect(1), new TapTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, true))));
    }

    private SkyHussar(final SkyHussar card) {
        super(card);
    }

    @Override
    public SkyHussar copy() {
        return new SkyHussar(this);
    }
}

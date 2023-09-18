
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;

/**
 * @author Loki
 */
public final class DesolationAngel extends CardImpl {

    private static final FilterLandPermanent filter2 = new FilterLandPermanent("lands you control");

    static {
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public DesolationAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Kicker (You may pay an additional as you cast this spell.)
        this.addAbility(new KickerAbility("{W}{W}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Desolation Angel enters the battlefield, destroy all lands you control. If it was kicked, destroy all lands instead.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(new DestroyAllEffect(StaticFilters.FILTER_LANDS),
                new DestroyAllEffect(filter2), KickedCondition.ONCE, "destroy all lands you control. If it was kicked, destroy all lands instead.")));
    }

    private DesolationAngel(final DesolationAngel card) {
        super(card);
    }

    @Override
    public DesolationAngel copy() {
        return new DesolationAngel(this);
    }
}

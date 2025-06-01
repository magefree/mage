
package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ImpetuousProtege extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("tapped creatures your opponents control");

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    private static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filter);
    private static final Hint hint = xValue.getHint();

    public ImpetuousProtege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Partner with Proud Mentor (When this creature enters the battlefield, target player may put Proud Mentor into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Proud Mentor"));

        // Whenever Impetuous Protege attacks, it gets +X/+0 until end of turn, where X is the greatest power among tapped creatures your opponents control
        this.addAbility(new AttacksTriggeredAbility(
                new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, "it")
        ).addHint(hint));
    }

    private ImpetuousProtege(final ImpetuousProtege card) {
        super(card);
    }

    @Override
    public ImpetuousProtege copy() {
        return new ImpetuousProtege(this);
    }
}
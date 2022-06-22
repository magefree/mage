package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class SurgeMare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SurgeMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Surge Mare can't be blocked by green creatures.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedByCreaturesSourceEffect(
                        filter, Duration.WhileOnBattlefield
                )
        ));

        // Whenever Surge Mare deals damage to an opponent, you may draw a card. If you do, discard a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1)
                        .setText("you may draw a card. If you do, discard a card"),
                true
        ));

        // {1}{U}: Surge Mare gets +2/-2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, -2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}{U}")
        ));
    }

    private SurgeMare(final SurgeMare card) {
        super(card);
    }

    @Override
    public SurgeMare copy() {
        return new SurgeMare(this);
    }
}

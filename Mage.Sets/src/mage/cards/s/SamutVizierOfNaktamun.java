package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamutVizierOfNaktamun extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control");

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    public SamutVizierOfNaktamun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, if that creature entered the battlefield this turn, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
                        .setText("if that creature entered the battlefield this turn, draw a card"),
                filter, false, SetTargetPointer.NONE, true
        ));
    }

    private SamutVizierOfNaktamun(final SamutVizierOfNaktamun card) {
        super(card);
    }

    @Override
    public SamutVizierOfNaktamun copy() {
        return new SamutVizierOfNaktamun(this);
    }
}

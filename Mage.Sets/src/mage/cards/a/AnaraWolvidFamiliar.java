package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnaraWolvidFamiliar extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CommanderPredicate.instance);
    }

    public AnaraWolvidFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as it's your turn, commanders you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(),
                        Duration.WhileOnBattlefield, filter
                ), MyTurnCondition.instance, "as long as it's your turn, " +
                "commanders you control have indestructible"
        )));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private AnaraWolvidFamiliar(final AnaraWolvidFamiliar card) {
        super(card);
    }

    @Override
    public AnaraWolvidFamiliar copy() {
        return new AnaraWolvidFamiliar(this);
    }
}

package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class YellowjacketHeartlessMarauder extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.VILLAIN, "another Villain you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public YellowjacketHeartlessMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another Villain you control enters, Yellowjacket gets +1/+0 and gains lifelink until end of turn.
        Ability ability = new EntersBattlefieldAllTriggeredAbility(
            new BoostSourceEffect(1, 0, Duration.EndOfTurn).setText("{this} gets +1/+0"), filter
        );
        ability.addEffect(new GainAbilitySourceEffect(
            LifelinkAbility.getInstance(), Duration.EndOfTurn).concatBy("and")
        );
        this.addAbility(ability);
    }

    private YellowjacketHeartlessMarauder(final YellowjacketHeartlessMarauder card) {
        super(card);
    }

    @Override
    public YellowjacketHeartlessMarauder copy() {
        return new YellowjacketHeartlessMarauder(this);
    }
}

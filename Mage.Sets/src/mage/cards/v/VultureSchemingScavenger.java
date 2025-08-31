package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VultureSchemingScavenger extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.VILLAIN, "Villains");

    public VultureSchemingScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U/B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Vulture attacks, other Villains you control gain flying until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn, filter, true
        )));
    }

    private VultureSchemingScavenger(final VultureSchemingScavenger card) {
        super(card);
    }

    @Override
    public VultureSchemingScavenger copy() {
        return new VultureSchemingScavenger(this);
    }
}

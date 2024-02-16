package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReaverTitan extends CardImpl {

    private static final FilterCard filter = new FilterCard("mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public ReaverTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // Void Shields -- Protection from mana value 3 or less.
        this.addAbility(new ProtectionAbility(filter).withFlavorWord("Void Shields"));

        // Gatling Blaster -- Whenever Reaver Titan attacks, it deals 5 damage to each opponent.
        this.addAbility(new AttacksTriggeredAbility(new DamagePlayersEffect(
                5, TargetController.OPPONENT, "it"
        )).withFlavorWord("Gatling Blaster"));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private ReaverTitan(final ReaverTitan card) {
        super(card);
    }

    @Override
    public ReaverTitan copy() {
        return new ReaverTitan(this);
    }
}


package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PatronOfTheAkki extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.GOBLIN, "Goblin");

    public PatronOfTheAkki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Goblin offering (You may cast this card any time you could cast an instant by sacrificing a Goblin and paying the difference in mana costs between this and the sacrificed Goblin. Mana cost includes color.)
        this.addAbility(new OfferingAbility(filter));

        // Whenever Patron of the Akki attacks, creatures you control get +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false), false));
    }

    private PatronOfTheAkki(final PatronOfTheAkki card) {
        super(card);
    }

    @Override
    public PatronOfTheAkki copy() {
        return new PatronOfTheAkki(this);
    }
}

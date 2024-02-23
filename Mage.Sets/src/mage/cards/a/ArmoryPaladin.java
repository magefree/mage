package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmoryPaladin extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Aura or Equipment spell");

    static {
        filter.add(Predicates.or(
                SubType.AURA.getPredicate(),
                SubType.EQUIPMENT.getPredicate()
        ));
    }

    public ArmoryPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you cast an Aura or Equipment spell, exile the top card of your library. You may play that card until the end of your next turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn), filter, false
        ));
    }

    private ArmoryPaladin(final ArmoryPaladin card) {
        super(card);
    }

    @Override
    public ArmoryPaladin copy() {
        return new ArmoryPaladin(this);
    }
}

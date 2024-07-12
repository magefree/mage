package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
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
public final class HarnesserOfStorms extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a noncreature or Otter spell");

    static {
        filter.add(Predicates.or(
                Predicates.not(CardType.CREATURE.getPredicate()),
                SubType.OTTER.getPredicate()
        ));
    }

    public HarnesserOfStorms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast a noncreature or Otter spell, you may exile the top card of your library. Until end of turn, you may play that card. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn), filter, true
        ).setTriggersLimitEachTurn(1));
    }

    private HarnesserOfStorms(final HarnesserOfStorms card) {
        super(card);
    }

    @Override
    public HarnesserOfStorms copy() {
        return new HarnesserOfStorms(this);
    }
}

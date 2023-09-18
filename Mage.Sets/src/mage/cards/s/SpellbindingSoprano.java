package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpellbindingSoprano extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public SpellbindingSoprano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Spellbinding Soprano attacks, instant and sorcery spells you cast this turn cost {1} less to cast.
        this.addAbility(new AttacksTriggeredAbility(new SpellsCostReductionControllerEffect(filter, 1).setDuration(Duration.EndOfTurn)));

        // Encore {3}{R}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{3}{R}")));
    }

    private SpellbindingSoprano(final SpellbindingSoprano card) {
        super(card);
    }

    @Override
    public SpellbindingSoprano copy() {
        return new SpellbindingSoprano(this);
    }
}

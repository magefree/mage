package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.keyword.RulebreakerAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class TheEverforger extends CardImpl {

    private static final FilterCard cardFilter = new FilterCard("can have artifact creature and Equipment cards of any color identity and any basic land cards");
    private static final FilterSpell spellFilter = new FilterSpell("an artifact creature or Equipment spell");

    static {
        cardFilter.add(Predicates.or(
            Predicates.and(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
            ),
            SubType.EQUIPMENT.getPredicate(),
            SuperType.BASIC.getPredicate()
        ));
        spellFilter.add(Predicates.or(
            Predicates.and(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
            ),
            SubType.EQUIPMENT.getPredicate()
        ));
    }

    public TheEverforger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Rulebreaker -- A deck with this commander can have artifact creature and Equipment cards of any color identity and any basic land cards.
        this.addAbility(new RulebreakerAbility(cardFilter));

        // Whenever you cast an artifact creature or Equipment spell, you may copy that spell. Do this only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CopyTargetStackObjectEffect(false, false, false),
            spellFilter,
            true,
            SetTargetPointer.SPELL
        ).setDoOnlyOnceEachTurn(true));
    }

    private TheEverforger(final TheEverforger card) {
        super(card);
    }

    @Override
    public TheEverforger copy() {
        return new TheEverforger(this);
    }
}

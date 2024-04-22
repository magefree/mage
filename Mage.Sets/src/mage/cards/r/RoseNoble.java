package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RoseNoble extends CardImpl {

    private static final FilterSpell filter
            = new FilterSpell("a Doctor spell or creature spell with doctor's companion");

    static {
        filter.add(Predicates.or(
                SubType.DOCTOR.getPredicate(),
                Predicates.and(
                        CardType.CREATURE.getPredicate(),
                        new AbilityPredicate(DoctorsCompanionAbility.class)
                )
        ));
    }

    public RoseNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever you cast a Doctor spell or creature spell with doctor's companion, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, false
        ));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private RoseNoble(final RoseNoble card) {
        super(card);
    }

    @Override
    public RoseNoble copy() {
        return new RoseNoble(this);
    }
}

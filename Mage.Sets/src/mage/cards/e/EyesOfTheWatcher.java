package mage.cards.e;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class EyesOfTheWatcher extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public EyesOfTheWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever you cast an instant or sorcery spell, you may pay {1}. If you do, scry 2.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(new ScryEffect(2), new ManaCostsImpl<>("{1}")), filter, false));
    }

    private EyesOfTheWatcher(final EyesOfTheWatcher card) {
        super(card);
    }

    @Override
    public EyesOfTheWatcher copy() {
        return new EyesOfTheWatcher(this);
    }
}

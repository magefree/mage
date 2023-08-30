package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class UpTheBeanstalk extends CardImpl {

    private static FilterSpell filter = new FilterSpell("spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 5));
    }

    public UpTheBeanstalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // When Up the Beanstalk enters the battlefield and whenever you cast a spell with mana value 5 or greater, draw a card.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), false,
                "When {this} enters the battlefield and whenever you cast a spell with mana value 5 or greater, ",
                new EntersBattlefieldTriggeredAbility(null),
                new SpellCastControllerTriggeredAbility(null, filter, false)
        ));
    }

    private UpTheBeanstalk(final UpTheBeanstalk card) {
        super(card);
    }

    @Override
    public UpTheBeanstalk copy() {
        return new UpTheBeanstalk(this);
    }
}
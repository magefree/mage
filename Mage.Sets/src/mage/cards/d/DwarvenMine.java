package mage.cards.d;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.DwarfToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DwarvenMine extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.MOUNTAIN);

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 3);

    public DwarvenMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {R}.)
        this.addAbility(new RedManaAbility());

        // Dwarven Mine enters the battlefield tapped unless you control three or more other Mountains.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition),
                "tapped unless you control three or more other Mountains"
        ));

        // When Dwarven Mine enters the battlefield untapped, create a 1/1 red Dwarf creature token.
        this.addAbility(new EntersBattlefieldUntappedTriggeredAbility(new CreateTokenEffect(new DwarfToken()), false));
    }

    private DwarvenMine(final DwarvenMine card) {
        super(card);
    }

    @Override
    public DwarvenMine copy() {
        return new DwarvenMine(this);
    }
}

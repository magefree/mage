
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ReflectionToken;
import mage.target.TargetPermanent;

/**
 * @author LevelX2
 */
public final class SpiritMirror extends CardImpl {

    private static final FilterPermanent filterToken = new FilterPermanent(SubType.REFLECTION, "Reflection token");
    private static final FilterPermanent filter = new FilterPermanent("Reflection");

    static {
        filterToken.add(SubType.REFLECTION.getPredicate());
        filterToken.add(TokenPredicate.TRUE);
        filter.add(SubType.REFLECTION.getPredicate());
    }

    public SpiritMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, if there are no Reflection tokens on the battlefield, create a 2/2 white Reflection creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new ReflectionToken()), TargetController.YOU, false),
                new PermanentsOnTheBattlefieldCondition(filterToken, ComparisonType.EQUAL_TO, 0, false),
                "At the beginning of your upkeep, if there are no Reflection tokens on the battlefield, create a 2/2 white Reflection creature token."));

        // {0}: Destroy target Reflection.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SpiritMirror(final SpiritMirror card) {
        super(card);
    }

    @Override
    public SpiritMirror copy() {
        return new SpiritMirror(this);
    }
}

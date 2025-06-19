package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ReflectionToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SpiritMirror extends CardImpl {

    private static final FilterPermanent filterToken = new FilterPermanent(SubType.REFLECTION, "there are no Reflection tokens on the battlefield");
    private static final FilterPermanent filter = new FilterPermanent("Reflection");

    static {
        filterToken.add(TokenPredicate.TRUE);
        filter.add(SubType.REFLECTION.getPredicate());
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filterToken, ComparisonType.EQUAL_TO, 0, false);

    public SpiritMirror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, if there are no Reflection tokens on the battlefield, create a 2/2 white Reflection creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new ReflectionToken())).withInterveningIf(condition));

        // {0}: Destroy target Reflection.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new GenericManaCost(0));
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

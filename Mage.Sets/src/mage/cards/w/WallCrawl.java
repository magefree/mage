package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.Spider21Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallCrawl extends CardImpl {

    private static final FilterCreaturePermanent filterSpiders
            = new FilterCreaturePermanent(SubType.SPIDER, "Spiders you control");
    static {
        filterSpiders.add(TargetController.YOU.getControllerPredicate());
    }
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SPIDER));
    private static final Hint hint = new ValueHint("Spiders you control", xValue);
    private static final FilterCreaturePermanent filterDefenders
            = new FilterCreaturePermanent("creatures with defender");
    static {
        filterDefenders.add(new AbilityPredicate(DefenderAbility.class));
    }

    public WallCrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When this enchantment enters, create a 2/1 green Spider creature token with reach, then you gain 1 life for each Spider you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Spider21Token()));
        ability.addEffect(new GainLifeEffect(xValue).concatBy(", then"));
        this.addAbility(ability.addHint(hint));

        // Spiders you control get +1/+1 and can't be blocked by creatures with defender.
        ability = new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filterSpiders, false
        ));
        ability.addEffect(new CantBeBlockedByCreaturesAllEffect(filterSpiders, filterDefenders, Duration.WhileOnBattlefield)
                .setText("and can't be blocked by creatures with defender"));
        this.addAbility(ability);
    }

    private WallCrawl(final WallCrawl card) {
        super(card);
    }

    @Override
    public WallCrawl copy() {
        return new WallCrawl(this);
    }
}

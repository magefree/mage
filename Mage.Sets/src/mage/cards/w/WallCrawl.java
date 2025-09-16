package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.Spider21Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallCrawl extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent(SubType.SPIDER, "Spiders you control");
    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.SPIDER));
    private static final Hint hint = new ValueHint("Spiders you control", xValue);

    public WallCrawl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // When this enchantment enters, create a 2/1 green Spider creature token with reach, then you gain 1 life for each Spider you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Spider21Token()));
        ability.addEffect(new GainLifeEffect(xValue).concatBy(", then"));
        this.addAbility(ability.addHint(hint));

        // Spiders you control get +1/+1 and can't be blocked by creatures with defender.
        ability = new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileControlled, filter, false
        ));
        ability.addEffect(new CantBeBlockedAllEffect(filter, Duration.WhileControlled)
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

package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarksteelSplicer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.PHYREXIAN, "nontoken Phyrexian");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    private static final FilterPermanent filter2 = new FilterPermanent(SubType.GOLEM, "Golems");

    public DarksteelSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Darksteel Splicer or another nontoken Phyrexian enters the battlefield under your control, create X 3/3 colorless Phyrexian Golem artifact creature tokens, where X is the number of opponents you have.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new PhyrexianGolemToken(), OpponentsCount.instance)
                        .setText("create X 3/3 colorless Phyrexian Golem artifact creature tokens, where X is the number of opponents you have"),
                filter, false, true
        ));

        // Golems you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));
    }

    private DarksteelSplicer(final DarksteelSplicer card) {
        super(card);
    }

    @Override
    public DarksteelSplicer copy() {
        return new DarksteelSplicer(this);
    }
}

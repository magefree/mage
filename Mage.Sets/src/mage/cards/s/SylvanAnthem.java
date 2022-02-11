package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SylvanAnthem extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a green creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public SylvanAnthem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}");

        // Green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        ).setText("green creatures you control get +1/+1")));

        // Whenever a green creature enters the battlefield under your control, scry 1.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ScryEffect(1, false), filter));
    }

    private SylvanAnthem(final SylvanAnthem card) {
        super(card);
    }

    @Override
    public SylvanAnthem copy() {
        return new SylvanAnthem(this);
    }
}

package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MobilizeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BoneCairnButcher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("attacking tokens");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TokenPredicate.TRUE);
    }

    public BoneCairnButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mobilize 2
        this.addAbility(new MobilizeAbility(2));

        // Attacking tokens you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private BoneCairnButcher(final BoneCairnButcher card) {
        super(card);
    }

    @Override
    public BoneCairnButcher copy() {
        return new BoneCairnButcher(this);
    }
}

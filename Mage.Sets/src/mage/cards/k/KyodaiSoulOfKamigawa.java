package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KyodaiSoulOfKamigawa extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public KyodaiSoulOfKamigawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kyodai, Soul of Kamigawa enters the battlefield, another target permanent gains indestructible for as long as you control Kyodai.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.WhileControlled
        ), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // {W}{U}{B}{R}{G}: Kyodai, Soul of Kamigawa gets +5/+5 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostSourceEffect(
                5, 5, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{W}{U}{B}{R}{G}")));
    }

    private KyodaiSoulOfKamigawa(final KyodaiSoulOfKamigawa card) {
        super(card);
    }

    @Override
    public KyodaiSoulOfKamigawa copy() {
        return new KyodaiSoulOfKamigawa(this);
    }
}

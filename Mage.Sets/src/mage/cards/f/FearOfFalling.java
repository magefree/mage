package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfFalling extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public FearOfFalling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Fear of Falling attacks, target creature defending player controls gets -2/-0 and loses flying until your next turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-2, 0, Duration.UntilYourNextTurn)
                .setText("target creature defending player controls gets -2/-0"));
        ability.addEffect(new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.UntilYourNextTurn).setText("and loses flying until your next turn"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private FearOfFalling(final FearOfFalling card) {
        super(card);
    }

    @Override
    public FearOfFalling copy() {
        return new FearOfFalling(this);
    }
}

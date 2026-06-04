package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

/**
 *
 * @author muz
 */
public final class ElektraDaughterOfTheHand extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls with power 3 or less");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 3));
    }

    public ElektraDaughterOfTheHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sneak {1}{B}{B}
        this.addAbility(new SneakAbility(this, "{1}{B}{B}"));

        // When Elektra enters, destroy target creature an opponent controls with power 3 or less.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ElektraDaughterOfTheHand(final ElektraDaughterOfTheHand card) {
        super(card);
    }

    @Override
    public ElektraDaughterOfTheHand copy() {
        return new ElektraDaughterOfTheHand(this);
    }
}

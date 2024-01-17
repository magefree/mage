package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KraulWhipcracker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("token an opponent controls");

    static {
        filter.add(TokenPredicate.TRUE);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public KraulWhipcracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Kraul Whipcracker enters the battlefield, destroy target token an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private KraulWhipcracker(final KraulWhipcracker card) {
        super(card);
    }

    @Override
    public KraulWhipcracker copy() {
        return new KraulWhipcracker(this);
    }
}

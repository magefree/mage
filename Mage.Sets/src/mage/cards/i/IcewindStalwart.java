package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcewindStalwart extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("non-Warrior creature you control");

    static {
        filter.add(Predicates.not(SubType.WARRIOR.getPredicate()));
    }

    public IcewindStalwart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection Fighting Style — When Icewind Stalwart enters the battlefield, exile up to one target non-Warrior creature you control, then return it to the battlefield under its owner's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileThenReturnTargetEffect(false, false));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability.withFlavorWord("Protection Fighting Style"));
    }

    private IcewindStalwart(final IcewindStalwart card) {
        super(card);
    }

    @Override
    public IcewindStalwart copy() {
        return new IcewindStalwart(this);
    }
}

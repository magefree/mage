package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author muz
 */
public final class GhostOfIllyria extends CardImpl {

    private static final FilterControlledPermanent filter =
        new FilterControlledPermanent("other target nonland permanent you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public GhostOfIllyria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, exile up to one other target nonland permanent you control.
        // Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileReturnBattlefieldNextEndStepTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private GhostOfIllyria(final GhostOfIllyria card) {
        super(card);
    }

    @Override
    public GhostOfIllyria copy() {
        return new GhostOfIllyria(this);
    }
}

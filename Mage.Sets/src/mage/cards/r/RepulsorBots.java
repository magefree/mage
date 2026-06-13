package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RepulsorBots extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("other artifacts and/or creatures");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            CardType.CREATURE.getPredicate()
        ));
    }

    public RepulsorBots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, return up to two other target artifacts and/or creatures to their owners' hands.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);
    }

    private RepulsorBots(final RepulsorBots card) {
        super(card);
    }

    @Override
    public RepulsorBots copy() {
        return new RepulsorBots(this);
    }
}

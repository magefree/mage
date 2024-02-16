package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OgreErrant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.KNIGHT, "another attacking Knight");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(AnotherPredicate.instance);
    }

    public OgreErrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Ogre Errant attacks, another target attacking Knight gains menace until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                new MenaceAbility(), Duration.EndOfTurn
        ).setText("another target attacking Knight gains menace until end of turn. " +
                "<i>(It can't be blocked except by two or more creatures.)</i>"), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OgreErrant(final OgreErrant card) {
        super(card);
    }

    @Override
    public OgreErrant copy() {
        return new OgreErrant(this);
    }
}

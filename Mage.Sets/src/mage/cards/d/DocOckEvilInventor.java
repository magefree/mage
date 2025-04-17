package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DocOckEvilInventor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DocOckEvilInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // At the beginning of combat on your turn, target noncreature artifact you control becomes an 8/8 Robot Villain artifact creature in addition to its other types.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(
                        8, 8,
                        "8/8 Robot Villain artifact creature",
                        SubType.ROBOT, SubType.VILLAIN
                ), false, false, Duration.Custom
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private DocOckEvilInventor(final DocOckEvilInventor card) {
        super(card);
    }

    @Override
    public DocOckEvilInventor copy() {
        return new DocOckEvilInventor(this);
    }
}

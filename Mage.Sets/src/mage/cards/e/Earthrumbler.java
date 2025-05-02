package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Earthrumbler extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public Earthrumbler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{G}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Exile an artifact or creature card from your graveyard: This Vehicle becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new AddCardTypeSourceEffect(Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(filter))
        ));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private Earthrumbler(final Earthrumbler card) {
        super(card);
    }

    @Override
    public Earthrumbler copy() {
        return new Earthrumbler(this);
    }
}

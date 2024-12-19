package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PartyThrasher extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("noncreature spells you cast from exile");

    static {
        filter.add(new CastFromZonePredicate(Zone.EXILED));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public PartyThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Noncreature spells you cast from exile have convoke.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)
        ));

        // At the beginning of your precombat main phase, you may discard a card. If you do, exile the top two cards of your library, then choose one of them. You may play that card this turn.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(new DoIfCostPaid(
                new ExileTopXMayPlayUntilEffect(2, true, Duration.EndOfTurn), new DiscardCardCost()
        )));
    }

    private PartyThrasher(final PartyThrasher card) {
        super(card);
    }

    @Override
    public PartyThrasher copy() {
        return new PartyThrasher(this);
    }
}

package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DailyBugleReporters extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value 2 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public DailyBugleReporters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, choose one --
        // * Puff Piece -- Put a +1/+1 counter on each of up to two target creatures.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        ability.withFirstModeFlavorWord("Puff Piece");

        // * Investigative Journalism -- Return target creature card with mana value 2 or less from your graveyard to your hand.
        ability.addMode(new Mode(new ReturnFromGraveyardToHandTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(filter))
                .withFlavorWord("Investigative Journalism"));
        this.addAbility(ability);
    }

    private DailyBugleReporters(final DailyBugleReporters card) {
        super(card);
    }

    @Override
    public DailyBugleReporters copy() {
        return new DailyBugleReporters(this);
    }
}

package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AleshaWhoLaughsAtFate extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal to {this}'s power from your graveyard"
    );

    static {
        filter.add(AleshaWhoLaughsAtFatePredicate.instance);
    }

    public AleshaWhoLaughsAtFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Alesha attacks, put a +1/+1 counter on it.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));

        // Raid -- At the beginning of your end step, if you attacked this turn, return target creature card with mana value less than or equal to Alesha's power from your graveyard to the battlefield.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
        ).withInterveningIf(RaidCondition.instance);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private AleshaWhoLaughsAtFate(final AleshaWhoLaughsAtFate card) {
        super(card);
    }

    @Override
    public AleshaWhoLaughsAtFate copy() {
        return new AleshaWhoLaughsAtFate(this);
    }
}

enum AleshaWhoLaughsAtFatePredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getManaValue)
                .map(p -> input.getObject().getManaValue() <= p)
                .orElse(false);
    }
}

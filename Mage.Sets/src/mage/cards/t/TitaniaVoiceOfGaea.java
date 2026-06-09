package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.effects.common.ReturnFromYourGraveyardToBattlefieldAllEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeGroupEvent;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitaniaVoiceOfGaea extends MeldCard {

    private static final Condition condition = new CompoundCondition(
            "there are four or more land cards in your graveyard and you " +
                    "both own and control {this} and a land named Argoth, Sanctum of Nature",
            new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_LAND),
            new MeldCondition("Argoth, Sanctum of Nature", CardType.LAND)
    );

    public TitaniaVoiceOfGaea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "{1}{G}{G}",
                "Titania, Gaea Incarnate",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL, SubType.AVATAR}, "G");
        this.meldsWithClazz = mage.cards.a.ArgothSanctumOfNature.class;

        // Titania, Voice of Gaea
        this.getLeftHalfCard().setPT(3, 4);

        // Reach
        this.getLeftHalfCard().addAbility(ReachAbility.getInstance());

        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        this.getLeftHalfCard().addAbility(new TitaniaVoiceOfGaeaTriggeredAbility());

        // At the beginning of your upkeep, if there are four or more land cards in your graveyard and you both own and control Titania, Voice of Gaea and a land named Argoth, Sanctum of Nature, exile them, then meld them into Titania, Gaea Incarnate.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new MeldEffect(
                "Argoth, Sanctum of Nature", "Titania, Gaea Incarnate"
        )).withInterveningIf(condition));

        // Titania, Gaea Incarnate
        this.getRightHalfCard().setPT(0, 0);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // Titania, Gaea Incarnate's power and toughness are each equal to the number of lands you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)
        ));

        // When Titania, Gaea Incarnate enters the battlefield, return all land cards from your graveyard to the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new ReturnFromYourGraveyardToBattlefieldAllEffect(
                StaticFilters.FILTER_CARD_LANDS, true)));

        // {3}{G}: Put four +1/+1 counters on target land you control. It becomes a 0/0 Elemental creature with haste. It's still a land.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "", SubType.ELEMENTAL)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ).setText("It becomes a 0/0 Elemental creature with haste. It's still a land."));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.getRightHalfCard().addAbility(ability);
    }

    private TitaniaVoiceOfGaea(final TitaniaVoiceOfGaea card) {
        super(card);
    }

    @Override
    public TitaniaVoiceOfGaea copy() {
        return new TitaniaVoiceOfGaea(this);
    }
}

class TitaniaVoiceOfGaeaTriggeredAbility extends TriggeredAbilityImpl {

    public TitaniaVoiceOfGaeaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GainLifeEffect(2));
    }

    private TitaniaVoiceOfGaeaTriggeredAbility(final TitaniaVoiceOfGaeaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TitaniaVoiceOfGaeaTriggeredAbility copy() {
        return new TitaniaVoiceOfGaeaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_GROUP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        return zEvent != null
                && zEvent.getToZone() == Zone.GRAVEYARD
                && zEvent.getCards() != null
                && zEvent.getCards()
                .stream()
                .filter(card -> card.isLand(game))
                .map(Card::getOwnerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.";
    }
}

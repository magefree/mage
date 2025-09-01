package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.ReturnFromGraveyardAtRandomEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViconiaDrowApostate extends CardImpl {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(4, StaticFilters.FILTER_CARD_CREATURES);
    private static final Hint hint = new ValueHint(
            "Creature cards in your graveyard",
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public ViconiaDrowApostate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, if there are four or more creature cards in your graveyard, return a creature card at random from your graveyard to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ReturnFromGraveyardAtRandomEffect(StaticFilters.FILTER_CARD_CREATURE, Zone.HAND)
        ).withInterveningIf(condition).addHint(hint));

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private ViconiaDrowApostate(final ViconiaDrowApostate card) {
        super(card);
    }

    @Override
    public ViconiaDrowApostate copy() {
        return new ViconiaDrowApostate(this);
    }
}

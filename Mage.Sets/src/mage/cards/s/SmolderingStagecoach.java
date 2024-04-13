package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SmolderingStagecoach extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_AND_SORCERY);
    private static final Hint hint = new ValueHint("Number of instant and sorcery cards in your graveyard", xValue);

    private static final FilterCard filterInstant = new FilterCard("instant spell");
    private static final FilterCard filterSorcery = new FilterCard("sorcery spell");

    static {
        filterInstant.add(CardType.INSTANT.getPredicate());
        filterSorcery.add(CardType.SORCERY.getPredicate());
    }

    public SmolderingStagecoach(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Smoldering Stagecoach's power is equal to the number of instant and sorcery cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(xValue)).addHint(hint));

        // Whenever Smoldering Stagecoach attacks, the next instant spell and the next sorcery spell you cast this turn each have cascade.
        Ability ability = new AttacksTriggeredAbility(null);
        ability.addEffect(new NextSpellCastHasAbilityEffect(new CascadeAbility(), filterInstant)
                .setText("the next instant spell"));
        ability.addEffect(new NextSpellCastHasAbilityEffect(new CascadeAbility(), filterSorcery)
                .setText("and the next sorcery spell you cast this turn each have cascade"));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private SmolderingStagecoach(final SmolderingStagecoach card) {
        super(card);
    }

    @Override
    public SmolderingStagecoach copy() {
        return new SmolderingStagecoach(this);
    }
}

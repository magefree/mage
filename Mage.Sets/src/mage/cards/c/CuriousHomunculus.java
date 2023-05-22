package mage.cards.c;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.ProwessAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.common.InstantOrSorcerySpellManaBuilder;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterInstantOrSorceryCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CuriousHomunculus extends TransformingDoubleFacedCard {

    private static final Condition condition
            = new CardsInControllerGraveyardCondition(3, StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);
    private static final FilterCard filter = new FilterInstantOrSorceryCard("Instant and sorcery spells");

    public CuriousHomunculus(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HOMUNCULUS}, "{1}{U}",
                "Voracious Reader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HOMUNCULUS}, ""
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(3, 4);

        // {T}: Add {C}. Spend this mana only to cast an instant or sorcery spell.
        this.getLeftHalfCard().addAbility(new ConditionalColorlessManaAbility(
                new TapSourceCost(), 1, new InstantOrSorcerySpellManaBuilder()
        ));

        // At the beginning of your upkeep, if there are three or more instant and/or sorcery cards in your graveyard, transform Curious Homunculus.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                condition, "At the beginning of your upkeep, if there are three or more " +
                "instant and/or sorcery cards in your graveyard, transform {this}"
        ));

        // Voracious Reader
        // Prowess
        this.getRightHalfCard().addAbility(new ProwessAbility());

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new SpellsCostReductionControllerEffect(filter, 1)
        ));
    }

    private CuriousHomunculus(final CuriousHomunculus card) {
        super(card);
    }

    @Override
    public CuriousHomunculus copy() {
        return new CuriousHomunculus(this);
    }
}

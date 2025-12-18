package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TithingBlade extends TransformingDoubleFacedCard {

    public TithingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{B}",
                "Consuming Sepulcher",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "B"
        );

        // Tithing Blade
        // When Tithing Blade enters the battlefield, each opponent sacrifices a creature.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE)
        ));

        // Craft with creature {4}{B}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{4}{B}", "creature", "another creature you control " +
                "or a creature card in your graveyard", CardType.CREATURE.getPredicate())
        );

        // Consuming Sepulcher
        // At the beginning of your upkeep, each opponent loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeOpponentsEffect(1)
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getRightHalfCard().addAbility(ability);
    }

    private TithingBlade(final TithingBlade card) {
        super(card);
    }

    @Override
    public TithingBlade copy() {
        return new TithingBlade(this);
    }
}

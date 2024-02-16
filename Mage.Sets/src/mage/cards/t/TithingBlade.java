package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TithingBlade extends CardImpl {

    public TithingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        this.secondSideCardClazz = mage.cards.c.ConsumingSepulcher.class;

        // When Tithing Blade enters the battlefield, each opponent sacrifices a creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeOpponentsEffect(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));

        // Craft with creature {4}{B}
        this.addAbility(new CraftAbility(
                "{4}{B}", "creature", "another creature you control " +
                "or a creature card in your graveyard", CardType.CREATURE.getPredicate())
        );
    }

    private TithingBlade(final TithingBlade card) {
        super(card);
    }

    @Override
    public TithingBlade copy() {
        return new TithingBlade(this);
    }
}

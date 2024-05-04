package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GiveScavengeContinuousEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class YoungDeathclaws extends CardImpl {

    public YoungDeathclaws(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Each creature card in your graveyard has scavenge. The scavenge cost is equal to its mana cost.
        this.addAbility(new SimpleStaticAbility(
                new GiveScavengeContinuousEffect(Duration.WhileOnBattlefield, StaticFilters.FILTER_CARD_CREATURE)
        ));
    }

    private YoungDeathclaws(final YoungDeathclaws card) {
        super(card);
    }

    @Override
    public YoungDeathclaws copy() {
        return new YoungDeathclaws(this);
    }
}

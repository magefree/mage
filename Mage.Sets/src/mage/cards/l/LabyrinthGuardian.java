package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class LabyrinthGuardian extends CardImpl {

    public LabyrinthGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Labyrinth Guardian becomes the target of a spell, sacrifice it.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new SacrificeSourceEffect(), StaticFilters.FILTER_SPELL_A));

        // Embalm {3}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{U}"), this));

    }

    private LabyrinthGuardian(final LabyrinthGuardian card) {
        super(card);
    }

    @Override
    public LabyrinthGuardian copy() {
        return new LabyrinthGuardian(this);
    }
}

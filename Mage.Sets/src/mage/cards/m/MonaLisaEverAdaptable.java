package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.MutagenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MonaLisaEverAdaptable extends CardImpl {

    public MonaLisaEverAdaptable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a player casts a creature spell, you create a Mutagen token.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new CreateTokenEffect(new MutagenToken()),
                StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));
    }

    private MonaLisaEverAdaptable(final MonaLisaEverAdaptable card) {
        super(card);
    }

    @Override
    public MonaLisaEverAdaptable copy() {
        return new MonaLisaEverAdaptable(this);
    }
}

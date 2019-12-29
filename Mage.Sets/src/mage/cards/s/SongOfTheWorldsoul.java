package mage.cards.s;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SongOfTheWorldsoul extends CardImpl {

    public SongOfTheWorldsoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // Whenever you cast a spell, populate.
        this.addAbility(new SpellCastControllerTriggeredAbility(new PopulateEffect(), false));
    }

    private SongOfTheWorldsoul(final SongOfTheWorldsoul card) {
        super(card);
    }

    @Override
    public SongOfTheWorldsoul copy() {
        return new SongOfTheWorldsoul(this);
    }
}

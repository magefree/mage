package mage.cards.e;

import mage.abilities.Mode;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author muz
 */
public final class EncounterTheUnknown extends CardImpl {

    public EncounterTheUnknown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // Choose one --
        // * Come in Peace -- Exile the top two cards of your library. Until the end of your next turn, you may play those cards.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn));
        this.getSpellAbility().withFirstModeFlavorWord("Come in Peace");

        // * Shoot to Kill -- Encounter the Unknown deals 2 damage to each creature and each player.
        this.getSpellAbility().addMode(new Mode(new DamageEverythingEffect(2)).withFlavorWord("Shoot to Kill"));
    }

    private EncounterTheUnknown(final EncounterTheUnknown card) {
        super(card);
    }

    @Override
    public EncounterTheUnknown copy() {
        return new EncounterTheUnknown(this);
    }
}

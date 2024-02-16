package mage.cards.s;

import mage.abilities.effects.common.RevealAndSeparatePilesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SteamAugury extends CardImpl {

    public SteamAugury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}");

        // Reveal the top five cards of your library and separate them into two piles. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.getSpellAbility().addEffect(new RevealAndSeparatePilesEffect(
                5, TargetController.YOU, TargetController.OPPONENT, Zone.GRAVEYARD
        ));
    }

    private SteamAugury(final SteamAugury card) {
        super(card);
    }

    @Override
    public SteamAugury copy() {
        return new SteamAugury(this);
    }
}

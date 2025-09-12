package mage.util;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Map;
import java.util.UUID;

/**
 * Helper class for fuzzy testing
 * <p>
 * Support:
 * - [x] enable by command line;
 * - [x] phased out permanents must be hidden
 * - [ ] TODO: out of range players and permanents must be hidden
 * - [ ] TODO: leave/disconnected players must be hidden
 * <p>
 * How-to use:
 * - enable here or by command line
 * - run unit tests and research the fails
 *
 * @author JayDi85
 */
public class FuzzyTestsUtil {

    /**
     * Phased out permanents must be hidden
     * Make sure other cards and effects do not see phased out permanents and ignore it
     * <p>
     * Use case:
     * - each permanent on battlefield will have copied phased out version with all abilities and effects
     * <p>
     * How-to use:
     * - set true or run with -Dxmage.tests.addRandomPhasedOutPermanents=true
     */
    public static boolean ADD_RANDOM_PHASED_OUT_PERMANENTS = false;

    static {
        String val = System.getProperty("xmage.tests.addRandomPhasedOutPermanents");
        if (val != null) {
            ADD_RANDOM_PHASED_OUT_PERMANENTS = Boolean.parseBoolean(val);
        }
    }

    /**
     * Create duplicated phased out permanent
     */
    public static void addRandomPhasedOutPermanent(Permanent originalPermanent, Ability source, Game game) {
        if (!ADD_RANDOM_PHASED_OUT_PERMANENTS) {
            return;
        }
        Player samplePlayer = game.getPlayers().values().stream().findFirst().orElse(null);
        if (samplePlayer == null || !samplePlayer.isTestMode()) {
            return;
        }

        // copy permanent and put it to battlefield as phased out (diff sides also supported here)
        // TODO: add phased out tests support (must not fail on it)
        Card originalCardSide = game.getCard(originalPermanent.getId());
        Card originalCardMain = originalCardSide.getMainCard();
        if (!originalCardMain.hasAbility(PhasingAbility.getInstance(), game)) {
            boolean canCreate = true;
            Card doppelgangerCardMain = game.copyCard(originalCardMain, source, originalPermanent.getControllerId());
            Map<UUID, MageObject> mapOldToNew = CardUtil.getOriginalToCopiedPartsMap(originalCardMain, doppelgangerCardMain);
            Card doppelgangerCardSide = (Card) mapOldToNew.getOrDefault(originalCardSide.getId(), null);
            doppelgangerCardSide.addAbility(PhasingAbility.getInstance());

            // compatibility workaround: remove all etb abilities to skip any etb choices (e.g. copy effect)
            doppelgangerCardSide.getAbilities().removeIf(a -> a.getEffects().stream().anyMatch(e -> e instanceof EntersBattlefieldEffect));
            // compatibility workaround: ignore aura to skip any etb choices (e.g. select new target)
            if (doppelgangerCardSide.hasSubtype(SubType.AURA, game)) {
                canCreate = false;
            }

            if (canCreate) {
                doppelgangerCardSide.putOntoBattlefield(game, Zone.BATTLEFIELD, source, originalPermanent.getControllerId());
                Permanent doppelgangerPerm = CardUtil.getPermanentFromCardPutToBattlefield(doppelgangerCardSide, game);
                doppelgangerPerm.phaseOut(game, true); // use indirect, so no phase in on untap
            }
        }
    }
}

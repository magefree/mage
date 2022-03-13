package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SunderingTitan extends CardImpl {

    public SunderingTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(10);

        // When Sundering Titan enters the battlefield or leaves the battlefield, choose a land of each basic land type, then destroy those lands.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new SunderingTitanDestroyLandEffect(), false));
    }

    private SunderingTitan(final SunderingTitan card) {
        super(card);
    }

    @Override
    public SunderingTitan copy() {
        return new SunderingTitan(this);
    }
}

class SunderingTitanDestroyLandEffect extends OneShotEffect {

    public SunderingTitanDestroyLandEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "choose a land of each basic land type, then destroy those lands";
    }

    public SunderingTitanDestroyLandEffect(final SunderingTitanDestroyLandEffect effect) {
        super(effect);
    }

    @Override
    public SunderingTitanDestroyLandEffect copy() {
        return new SunderingTitanDestroyLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Set<UUID> lands = new HashSet<>();
        if (controller != null && sourcePermanent != null) {
            for (SubType landName : SubType.getBasicLands()) {
                FilterLandPermanent filter = new FilterLandPermanent(landName + " to destroy");
                filter.add(landName.getPredicate());
                Target target = new TargetLandPermanent(1, 1, filter, true);
                if (target.canChoose(source.getControllerId(), source, game)) {
                    controller.chooseTarget(outcome, target, source, game);
                    lands.add(target.getFirstTarget());
                }
            }
            if (!lands.isEmpty()) {
                int destroyedLands = 0;
                for (UUID landId : lands) {
                    Permanent land = game.getPermanent(landId);
                    if (land != null) {
                        if (land.destroy(source, game, false)) {
                            destroyedLands++;
                        }
                    }
                }
                game.informPlayers(sourcePermanent.getLogName() + ": " + destroyedLands + (destroyedLands > 1 ? " lands were" : "land was") + " destroyed");
            }
            return true;
        }
        return false;
    }
}

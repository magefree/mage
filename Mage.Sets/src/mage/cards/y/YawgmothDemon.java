package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class YawgmothDemon extends CardImpl {

    public YawgmothDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // At the beginning of your upkeep, you may sacrifice an artifact. If you don't, tap Yawgmoth Demon and it deals 2 damage to you.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new YawgmothDemonEffect(), TargetController.YOU, false);
        this.addAbility(ability);
    }

    private YawgmothDemon(final YawgmothDemon card) {
        super(card);
    }

    @Override
    public YawgmothDemon copy() {
        return new YawgmothDemon(this);
    }
}

class YawgmothDemonEffect extends OneShotEffect {

    public YawgmothDemonEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may sacrifice an artifact. If you don't, tap {this} and it deals 2 damage to you";
    }

    public YawgmothDemonEffect(final YawgmothDemonEffect effect) {
        super(effect);
    }

    @Override
    public YawgmothDemonEffect copy() {
        return new YawgmothDemonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int artifacts = game.getBattlefield().countAll(new FilterArtifactPermanent(), source.getControllerId(), game);
            boolean artifactSacrificed = false;
            if (artifacts > 0 && controller.chooseUse(outcome, "Sacrifice an artifact?", source, game)) {
                if (new SacrificeControllerEffect(new FilterArtifactPermanent(), 1, "").apply(game, source)) {
                    artifactSacrificed = true;
                }
            }
            if (!artifactSacrificed) {
                Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
                if (sourceObject != null) {
                    sourceObject.tap(source, game);
                    controller.damage(2, source.getSourceId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}

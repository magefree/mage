package mage.cards.k;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.hint.common.ModesAlreadyUsedHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author muz
 */
public final class KimoyoBeads extends CardImpl {

    public KimoyoBeads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your end step, choose one that hasn't been chosen --
        // * AV Bead -- Draw a card.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
            new DrawCardSourceControllerEffect(1)
        );
        ability.getModes().getMode().withFlavorWord("AV Bead");
        ability.setModeTag("draw a card");
        ability.getModes().setLimitUsageByOnce(false);

        // * Communication Bead -- Create two 1/1 white Soldier creature tokens.
        ability.addMode(new Mode(new CreateTokenEffect(new SoldierToken(), 2))
            .withFlavorWord("Communication Bead")
            .setModeTag("create soldier tokens")
        );

        // * Prime Bead -- You gain 3 life. Exile this artifact, then return it to the battlefield under its owner's control.
        ability.addMode(new Mode(new GainLifeEffect(3))
            .addEffect(new KimoyoBeadsEffect())
            .withFlavorWord("Prime Bead")
            .setModeTag("gain life, and exile then return"));

        ability.addHint(ModesAlreadyUsedHint.instance);
        this.addAbility(ability);
    }

    private KimoyoBeads(final KimoyoBeads card) {
        super(card);
    }

    @Override
    public KimoyoBeads copy() {
        return new KimoyoBeads(this);
    }
}

class KimoyoBeadsEffect extends OneShotEffect {

    KimoyoBeadsEffect() {
        super(Outcome.Benefit);
        staticText = "exile {this}, then return it to the battlefield under its owner's control";
    }

    private KimoyoBeadsEffect(final KimoyoBeadsEffect effect) {
        super(effect);
    }

    @Override
    public KimoyoBeadsEffect copy() {
        return new KimoyoBeadsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (card instanceof PermanentToken) {
            return true;
        }
        Player owner = game.getPlayer(card.getOwnerId());
        return owner == null || owner.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

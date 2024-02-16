package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SirensRuse extends CardImpl {

    public SirensRuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target creature you control, then return that card to the battlefield under its owner's control.  If a Pirate was exiled this way, draw a card.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new SirensRuseEffect());
    }

    private SirensRuse(final SirensRuse card) {
        super(card);
    }

    @Override
    public SirensRuse copy() {
        return new SirensRuse(this);
    }
}

class SirensRuseEffect extends ExileTargetForSourceEffect {

    SirensRuseEffect() {
        super();
        this.staticText = "Exile target creature you control, then return that card to the battlefield under its owner's control. If a Pirate was exiled this way, draw a card.";
    }

    private SirensRuseEffect(final SirensRuseEffect effect) {
        super(effect);
    }

    @Override
    public SirensRuseEffect copy() {
        return new SirensRuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean isPirate = false;
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null && permanent.hasSubtype(SubType.PIRATE, game)) {
            isPirate = true;
        }
        if (super.apply(game, source)) {
            new ReturnToBattlefieldUnderYourControlTargetEffect(false).apply(game, source);
            if (isPirate && player != null) {
                player.drawCards(1, source, game);
            }
            return true;
        }
        return false;
    }
}

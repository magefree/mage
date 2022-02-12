package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public final class ZamWesell extends CardImpl {

    public ZamWesell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER, SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you cast Zam Wesell, target opponent reveals their hand. You may choose a creature card from it and have Zam Wesell enter the battlefield as a copy of that creature card.
        // TODO: Zam Wesell must be reworked to use on cast + etb abilities
        Ability ability = new CastSourceTriggeredAbility(new RevealHandTargetEffect());
        ability.addEffect(new ZamWesselEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ZamWesell(final ZamWesell card) {
        super(card);
    }

    @Override
    public ZamWesell copy() {
        return new ZamWesell(this);
    }
}

class ZamWesselEffect extends OneShotEffect {

    public ZamWesselEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may choose a creature card from it and have {this} enter the battlefield as a copy of that creature card";
    }

    public ZamWesselEffect(final ZamWesselEffect effect) {
        super(effect);
    }

    @Override
    public ZamWesselEffect copy() {
        return new ZamWesselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                TargetCard targetCard = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                controller.choose(outcome, targetPlayer.getHand(), targetCard, game);
                Card copyFromCard = game.getCard(targetCard.getFirstTarget());
                if (copyFromCard != null) {
                    game.informPlayers(controller.getLogName() + " chooses to copy " + copyFromCard.getName());
                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
                    game.addEffect(copyEffect, source);
                }
            }
            return true;
        }
        return false;
    }
}

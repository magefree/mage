package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.FixedCondition;
import mage.abilities.condition.common.AttachedCondition;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.*;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo, Merlingilb
 */
public final class ZamWesell extends CardImpl {

    public ZamWesell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER, SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you cast Zam Wesell, target opponent reveals their hand. You may choose a creature card from it and have Zam Wesell enter the battlefield as a copy of that creature card.
        Ability ability = new EntersBattlefieldAbility(new ZamWesselEffect(), new FixedCondition(true), "When you cast {this}, target opponent reveals his or her hand. You may choose a creature card from it and have {this} enter the battlefield as a copy of that creature card.", "");
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
        this.staticText = "";
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
        Player you = game.getPlayer(source.getControllerId());
        TargetPlayer targetOpponent = new TargetOpponent();
        if (you.chooseTarget(Outcome.Benefit, targetOpponent, source, game)) {
            Player targetPlayer = game.getPlayer(targetOpponent.getFirstTarget());
            if (targetPlayer != null && you != null) {
                if (!targetPlayer.getHand().isEmpty()) {
                    TargetCard target = new TargetCard(0, 1, Zone.HAND, StaticFilters.FILTER_CARD_CREATURE);
                    if (you.chooseTarget(Outcome.Benefit, targetPlayer.getHand(), target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            ContinuousEffect copyEffect = new CopyEffect(Duration.EndOfGame, card.getMainCard(), source.getSourceId());
                            copyEffect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
                            game.addEffect(copyEffect, source);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

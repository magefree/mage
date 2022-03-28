
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class BodyDouble extends CardImpl {

    public BodyDouble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Body Double enter the battlefield as a copy of any creature card in a graveyard.
        this.addAbility(new EntersBattlefieldAbility(new BodyDoubleCopyEffect(), true));

    }

    private BodyDouble(final BodyDouble card) {
        super(card);
    }

    @Override
    public BodyDouble copy() {
        return new BodyDouble(this);
    }
}

class BodyDoubleCopyEffect extends OneShotEffect {

    public BodyDoubleCopyEffect() {
        super(Outcome.Copy);
        this.staticText = "as a copy of any creature card in a graveyard";
    }

    public BodyDoubleCopyEffect(final BodyDoubleCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetCardInGraveyard(new FilterCreatureCard("creature card in a graveyard"));
            target.setNotTarget(true);
            if (target.canChoose(source.getControllerId(), source, game)) {
                player.choose(outcome, target, source, game);
                Card copyFromCard = game.getCard(target.getFirstTarget());
                if (copyFromCard != null) {
                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
                    game.addEffect(copyEffect, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BodyDoubleCopyEffect copy() {
        return new BodyDoubleCopyEffect(this);
    }
}


package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetDiscard;

/**
 *
 * @author jeffwadsworth
 */
public final class SpellboundDragon extends CardImpl {

    public SpellboundDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Spellbound Dragon attacks, draw a card, then discard a card. Spellbound Dragon gets +X/+0 until end of turn, where X is the discarded card's converted mana cost.
        this.addAbility(new AttacksTriggeredAbility(new SpellboundDragonEffect(), false));

    }

    public SpellboundDragon(final SpellboundDragon card) {
        super(card);
    }

    @Override
    public SpellboundDragon copy() {
        return new SpellboundDragon(this);
    }
}

class SpellboundDragonEffect extends OneShotEffect {

    public SpellboundDragonEffect() {
        super(Outcome.BoostCreature);
        staticText = "draw a card, then discard a card. Spellbound Dragon gets +X/+0 until end of turn, where X is the discarded card's converted mana cost";
    }

    public SpellboundDragonEffect(final SpellboundDragonEffect effect) {
        super(effect);
    }

    @Override
    public SpellboundDragonEffect copy() {
        return new SpellboundDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Permanent dragon = game.getPermanent(source.getSourceId());
        you.drawCards(1, game);
        TargetDiscard target = new TargetDiscard(you.getId());
        you.choose(Outcome.Discard, target, source.getSourceId(), game);
        Card card = you.getHand().get(target.getFirstTarget(), game);
        if (card != null && you.discard(card, source, game)) {
            int cmc = card.getConvertedManaCost();
            if (dragon != null) {
                game.addEffect(new BoostSourceEffect(cmc, 0, Duration.EndOfTurn), source);
                return true;
            }
        }
        return false;
    }
}
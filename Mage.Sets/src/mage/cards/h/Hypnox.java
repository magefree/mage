
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CastFromHandSourceCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.CastFromHandWatcher;

/**
 *
 * @author L_J
 */
public final class Hypnox extends CardImpl {

    public Hypnox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}{B}{B}{B}");
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hypnox enters the battlefield, if you cast it from your hand, exile all cards from target opponent's hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new HypnoxExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, CastFromHandSourceCondition.instance,
                "When {this} enters the battlefield, if you cast it from your hand, exile all cards from target opponent's hand."), new CastFromHandWatcher());

        // When Hypnox leaves the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new HypnoxReturnEffect(), false));
    }

    public Hypnox(final Hypnox card) {
        super(card);
    }

    @Override
    public Hypnox copy() {
        return new Hypnox(this);
    }
}

class HypnoxExileEffect extends OneShotEffect {
    HypnoxExileEffect() {
        super(Outcome.Exile);
        staticText = "Exile all cards from target opponent's hand";
    }

    HypnoxExileEffect(final HypnoxExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            for (UUID cid : player.getHand().copy()) {
                Card c = game.getCard(cid);
                if (c != null) {
                    c.moveToExile(source.getSourceId(), "Hypnox", source.getSourceId(), game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public HypnoxExileEffect copy() {
        return new HypnoxExileEffect(this);
    }

 }
 
class HypnoxReturnEffect extends OneShotEffect {

    public HypnoxReturnEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled cards to their owner's hand";
    }

    public HypnoxReturnEffect(final HypnoxReturnEffect effect) {
        super(effect);
    }

    @Override
    public HypnoxReturnEffect copy() {
        return new HypnoxReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exZone = game.getExile().getExileZone(source.getSourceId());
            if (exZone != null) {
                return controller.moveCards(exZone.getCards(game), Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}

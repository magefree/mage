
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author emerald000
 */
public final class MarduWoeReaper extends CardImpl {

    public MarduWoeReaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Mardu Woe-Reaper or another Warrior enters the battlefield under your control, you may exile target creature card from a graveyard. If you do, you gain 1 life.
        Ability ability = new MarduWoeReaperTriggeredAbility();
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public MarduWoeReaper(final MarduWoeReaper card) {
        super(card);
    }

    @Override
    public MarduWoeReaper copy() {
        return new MarduWoeReaper(this);
    }
}

class MarduWoeReaperTriggeredAbility extends TriggeredAbilityImpl {

    MarduWoeReaperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MarduWoeReaperEffect(), true);
    }

    MarduWoeReaperTriggeredAbility(final MarduWoeReaperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarduWoeReaperTriggeredAbility copy() {
        return new MarduWoeReaperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && (permanent.getId().equals(this.getSourceId()) || permanent.hasSubtype(SubType.WARRIOR, game))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Warrior enters the battlefield under your control, " + super.getRule();
    }
}

class MarduWoeReaperEffect extends OneShotEffect {

    MarduWoeReaperEffect() {
        super(Outcome.GainLife);
        this.staticText = "you may exile target creature card from a graveyard. If you do, you gain 1 life";
    }

    MarduWoeReaperEffect(final MarduWoeReaperEffect effect) {
        super(effect);
    }

    @Override
    public MarduWoeReaperEffect copy() {
        return new MarduWoeReaperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (player != null && card != null) {
            if (player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true)) {
                player.gainLife(1, game, source);
            }
            return true;
        }
        return false;
    }
}

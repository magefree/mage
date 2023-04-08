package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author L_J
 */
public final class EntrailsFeaster extends CardImpl {

    public EntrailsFeaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, you may exile a creature card from a graveyard. If you do, put a +1/+1 counter on Entrails Feaster. If you don't, tap Entrails Feaster.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new EntrailsFeasterEffect(), TargetController.YOU, false);
        this.addAbility(ability);

    }

    private EntrailsFeaster(final EntrailsFeaster card) {
        super(card);
    }

    @Override
    public EntrailsFeaster copy() {
        return new EntrailsFeaster(this);
    }
}

class EntrailsFeasterEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card from a graveyard");

    public EntrailsFeasterEffect() {
        super(Outcome.Detriment);
        this.staticText = "you may exile a creature card from a graveyard. If you do, put a +1/+1 counter on {this}. If you don't, tap {this}";
    }

    public EntrailsFeasterEffect(final EntrailsFeasterEffect effect) {
        super(effect);
    }

    @Override
    public EntrailsFeasterEffect copy() {
        return new EntrailsFeasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && source.getSourceId() != null) {
            Permanent sourceObject = source.getSourcePermanentIfItStillExists(game);
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            target.setNotTarget(true);
            if (target.canChoose(controller.getId(), source, game) && controller.chooseUse(outcome, "Exile a creature card from a graveyard?", source, game)) {
                if (controller.choose(Outcome.Exile, target, source, game)) {
                    Card cardChosen = game.getCard(target.getFirstTarget());
                    if (cardChosen != null) {
                        controller.moveCardsToExile(cardChosen, source, game, true, null, "");
                        if (sourceObject != null) {
                            sourceObject.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                            game.informPlayers(controller.getLogName() + " puts a +1/+1 counter on " + sourceObject.getLogName());
                        }
                    }
                } else if (sourceObject != null) {
                    sourceObject.tap(source, game);
                }
            } else if (sourceObject != null) {
                sourceObject.tap(source, game);
            }
            return true;
        }
        return false;
    }
}

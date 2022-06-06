
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ImpromptuRaid extends CardImpl {

    public ImpromptuRaid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R/G}");

        // {2}{RG}: Reveal the top card of your library. If it isn't a creature card, put it into your graveyard. Otherwise, put that card onto the battlefield. That creature gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ImpromptuRaidEffect(), new ManaCostsImpl<>("{2}{R/G}")));

    }

    private ImpromptuRaid(final ImpromptuRaid card) {
        super(card);
    }

    @Override
    public ImpromptuRaid copy() {
        return new ImpromptuRaid(this);
    }
}

class ImpromptuRaidEffect extends OneShotEffect {

    private static final FilterCard filterPutInGraveyard = new FilterCard("noncreature card to put into your graveyard");

    static {
        filterPutInGraveyard.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public ImpromptuRaidEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top card of your library. If it isn't a creature card, put it into your graveyard. Otherwise, put that card onto the battlefield. That creature gains haste. Sacrifice it at the beginning of the next end step";
    }

    public ImpromptuRaidEffect(final ImpromptuRaidEffect effect) {
        super(effect);
    }

    @Override
    public ImpromptuRaidEffect copy() {
        return new ImpromptuRaidEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl();
                cards.add(card);
                controller.revealCards(sourceObject.getName(), cards, game);
                if (filterPutInGraveyard.match(card, source.getControllerId(), source, game)) {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    return true;
                }
                if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                        SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("", source.getControllerId());
                        sacrificeEffect.setTargetPointer(new FixedTarget(permanent, game));
                        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

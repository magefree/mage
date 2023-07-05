package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class KitesailFreebooter extends CardImpl {

    public KitesailFreebooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Kitesail Freebooter enters the battlefield, target opponent reveals their hand. You choose a noncreature, nonland card from it. Exile that card until Kitesail Freebooter leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KitesailFreebooterExileEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private KitesailFreebooter(final KitesailFreebooter card) {
        super(card);
    }

    @Override
    public KitesailFreebooter copy() {
        return new KitesailFreebooter(this);
    }
}

class KitesailFreebooterExileEffect extends OneShotEffect {

    public KitesailFreebooterExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "target opponent reveals their hand. You choose a noncreature, nonland card from it. Exile that card until {this} leaves the battlefield";
    }

    public KitesailFreebooterExileEffect(final KitesailFreebooterExileEffect effect) {
        super(effect);
    }

    @Override
    public KitesailFreebooterExileEffect copy() {
        return new KitesailFreebooterExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && opponent != null && sourcePermanent != null) {
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards(sourcePermanent.getIdName(), opponent.getHand(), game);

                FilterCard filter = new FilterNonlandCard("noncreature, nonland card to exile");
                filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (opponent.getHand().count(filter, game) > 0 && controller.choose(Outcome.Exile, opponent.getHand(), target, source, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card == null) {
                        return true;
                    }
                    // If source permanent leaves the battlefield before its triggered ability resolves, the target card won't be exiled.
                    Effect effect = new ExileUntilSourceLeavesEffect(Zone.HAND);
                    effect.setTargetPointer(new FixedTarget(card, game));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;

    }
}

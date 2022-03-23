
package mage.cards.p;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class PrismaticStrands extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PrismaticStrands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent all damage that sources of the color of your choice would deal this turn.
        this.getSpellAbility().addEffect(new PrismaticStrandsEffect());

        // Flashback-Tap an untapped white creature you control.
        this.addAbility(new FlashbackAbility(this, new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false))));
    }

    private PrismaticStrands(final PrismaticStrands card) {
        super(card);
    }

    @Override
    public PrismaticStrands copy() {
        return new PrismaticStrands(this);
    }
}

class PrismaticStrandsEffect extends OneShotEffect {

    PrismaticStrandsEffect() {
        super(Outcome.PreventDamage);
        this.staticText = "Prevent all damage that sources of the color of your choice would deal this turn";
    }

    PrismaticStrandsEffect(final PrismaticStrandsEffect effect) {
        super(effect);
    }

    @Override
    public PrismaticStrandsEffect copy() {
        return new PrismaticStrandsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            ChoiceColor choice = new ChoiceColor();
            controller.choose(Outcome.PreventDamage, choice, game);
            if (choice.isChosen()) {
                if (!game.isSimulation()) {
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen sources of the color " + choice.getChoice());
                }
                game.addEffect(new PrismaticStrandsPreventionEffect(choice.getColor()), source);
                return true;
            }
        }
        return false;
    }
}

class PrismaticStrandsPreventionEffect extends PreventionEffectImpl {

    private final ObjectColor color;

    PrismaticStrandsPreventionEffect(ObjectColor color) {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.color = color;
    }

    PrismaticStrandsPreventionEffect(PrismaticStrandsPreventionEffect effect) {
        super(effect);
        this.color = effect.color;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                    || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
                MageObject sourceObject = game.getObject(event.getSourceId());
                if (sourceObject != null && sourceObject.getColor(game).shares(this.color)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PrismaticStrandsPreventionEffect copy() {
        return new PrismaticStrandsPreventionEffect(this);
    }
}

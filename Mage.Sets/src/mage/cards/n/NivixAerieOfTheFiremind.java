
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public final class NivixAerieOfTheFiremind extends CardImpl {

    public NivixAerieOfTheFiremind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}{U}{R}, {tap}: Exile the top card of your library. Until your next turn, you may cast that card if it's an instant or sorcery card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NivixAerieOfTheFiremindEffect(), new ManaCostsImpl<>("{2}{U}{R}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private NivixAerieOfTheFiremind(final NivixAerieOfTheFiremind card) {
        super(card);
    }

    @Override
    public NivixAerieOfTheFiremind copy() {
        return new NivixAerieOfTheFiremind(this);
    }
}

class NivixAerieOfTheFiremindEffect extends OneShotEffect {

    NivixAerieOfTheFiremindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile the top card of your library. Until your next turn, you may cast it if it's an instant or sorcery spell";
    }

    NivixAerieOfTheFiremindEffect(final NivixAerieOfTheFiremindEffect effect) {
        super(effect);
    }

    @Override
    public NivixAerieOfTheFiremindEffect copy() {
        return new NivixAerieOfTheFiremindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Library library = controller.getLibrary();
            if (library.hasCards()) {
                Card card = library.getFromTop(game);
                if (card != null
                        && controller.moveCardsToExile(card, source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null))
                        && card.isInstantOrSorcery(game)) {
                    ContinuousEffect effect = new NivixAerieOfTheFiremindCanCastEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}

class NivixAerieOfTheFiremindCanCastEffect extends AsThoughEffectImpl {

    NivixAerieOfTheFiremindCanCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "Until your next turn, you may cast that card";
    }

    NivixAerieOfTheFiremindCanCastEffect(final NivixAerieOfTheFiremindCanCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NivixAerieOfTheFiremindCanCastEffect copy() {
        return new NivixAerieOfTheFiremindCanCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && objectId.equals(this.getTargetPointer().getFirst(game, source));
    }
}

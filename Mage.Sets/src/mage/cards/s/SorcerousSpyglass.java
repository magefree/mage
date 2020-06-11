package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SorcerousSpyglass extends CardImpl {

    public SorcerousSpyglass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Sorcerous Spyglass enters the battlefield, look at an opponent's hand, then choose any card name.
        this.addAbility(new AsEntersBattlefieldAbility(new SorcerousSpyglassEntersEffect()));

        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SorcerousSpyglassActivationEffect()));
    }

    public SorcerousSpyglass(final SorcerousSpyglass card) {
        super(card);
    }

    @Override
    public SorcerousSpyglass copy() {
        return new SorcerousSpyglass(this);
    }
}

class SorcerousSpyglassEntersEffect extends ChooseACardNameEffect {

    SorcerousSpyglassEntersEffect() {
        super(ChooseACardNameEffect.TypeOfName.ALL);
        staticText = "look at an opponent's hand, then choose any card name";
    }

    SorcerousSpyglassEntersEffect(final SorcerousSpyglassEntersEffect effect) {
        super(effect);
    }

    @Override
    public SorcerousSpyglassEntersEffect copy() {
        return new SorcerousSpyglassEntersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetOpponent target = new TargetOpponent(true);
            if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    MageObject sourceObject = game.getObject(source.getSourceId());
                    player.lookAtCards(sourceObject != null ? sourceObject.getIdName() : null, opponent.getHand(), game);
                    player.chooseUse(Outcome.Benefit, "Press ok to name a card", "You won't be able to resize the window once you do", "Ok", " ", source, game);
                }
            }
        }
        return super.apply(game, source);
    }
}

class SorcerousSpyglassActivationEffect extends ContinuousRuleModifyingEffectImpl {

    public SorcerousSpyglassActivationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated unless they're mana abilities";
    }

    public SorcerousSpyglassActivationEffect(final SorcerousSpyglassActivationEffect effect) {
        super(effect);
    }

    @Override
    public SorcerousSpyglassActivationEffect copy() {
        return new SorcerousSpyglassActivationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if (ability.isPresent() && object != null) {
            return game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId()) // controller in range
                    && ability.get().getAbilityType() != AbilityType.MANA
                    && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}

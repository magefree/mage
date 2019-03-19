package mage.cards.p;

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth, nox
 */
public final class PithingNeedle extends CardImpl {

    public PithingNeedle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // As Pithing Needle enters the battlefield, name a card.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL)));

        // Activated abilities of sources with the chosen name can't be activated unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PithingNeedleEffect()));
    }

    public PithingNeedle(final PithingNeedle card) {
        super(card);
    }

    @Override
    public PithingNeedle copy() {
        return new PithingNeedle(this);
    }
}

class PithingNeedleEffect extends ContinuousRuleModifyingEffectImpl {

    public PithingNeedleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Activated abilities of sources with the chosen name can't be activated unless they're mana abilities";
    }

    public PithingNeedleEffect(final PithingNeedleEffect effect) {
        super(effect);
    }

    @Override
    public PithingNeedleEffect copy() {
        return new PithingNeedleEffect(this);
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
        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        if (ability.isPresent() 
                && object != null) {
            if (game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId()) // controller in range
                    && !(ability.get() instanceof ActivatedManaAbilityImpl) // not an activated mana ability
                    && object.getName().equals(game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY))) {
                return true;
            }
        }
        return false;
    }
}

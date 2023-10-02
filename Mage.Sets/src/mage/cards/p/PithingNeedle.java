package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
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

    private PithingNeedle(final PithingNeedle card) {
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

    private PithingNeedleEffect(final PithingNeedleEffect effect) {
        super(effect);
    }

    @Override
    public PithingNeedleEffect copy() {
        return new PithingNeedleEffect(this);
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
        if (ability.isPresent()
                && object != null) {
            return game.getState().getPlayersInRange(source.getControllerId(), game).contains(event.getPlayerId()) // controller in range
                    && !(ability.get() instanceof ActivatedManaAbilityImpl) // not an activated mana ability
                    && CardUtil.haveSameNames(object, cardName, game);
        }
        return false;
    }
}

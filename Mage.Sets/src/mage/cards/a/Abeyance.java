
package mage.cards.a;

import java.util.Optional;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class Abeyance extends CardImpl {

    public Abeyance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Until end of turn, target player can't cast instant or sorcery spells, and that player can't activate abilities that aren't mana abilities.
        this.getSpellAbility().addEffect(new AbeyanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public Abeyance(final Abeyance card) {
        super(card);
    }

    @Override
    public Abeyance copy() {
        return new Abeyance(this);
    }
}

class AbeyanceEffect extends ContinuousRuleModifyingEffectImpl {

    public AbeyanceEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Until end of turn, target player can't cast instant or sorcery spells, and that player can't activate abilities that aren't mana abilities";
    }

    public AbeyanceEffect(final AbeyanceEffect effect) {
        super(effect);
    }

    @Override
    public AbeyanceEffect copy() {
        return new AbeyanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast instant or sorcery spells or activate abilities that aren't mana abilities this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source.getFirstTarget() != null && source.getFirstTarget().equals(event.getPlayerId())) {
            MageObject object = game.getObject(event.getSourceId());
            if (event.getType() == GameEvent.EventType.CAST_SPELL) {
                if (object.isInstant() || object.isSorcery()) {
                    return true;
                }
            }
            if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
                Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
                if (ability.isPresent() && !(ability.get() instanceof ActivatedManaAbilityImpl)) {
                    return true;
                }
            }
        }
        return false;
    }
}

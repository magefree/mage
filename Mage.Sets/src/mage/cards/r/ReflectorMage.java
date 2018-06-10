
package mage.cards.r;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ReflectorMage extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ReflectorMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Reflector Mage enters the battlefield, return target creature an opponent controls to its owner's hand. That creature's owner can't cast spells with the same name as that creature until your next turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReflectorMageEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public ReflectorMage(final ReflectorMage card) {
        super(card);
    }

    @Override
    public ReflectorMage copy() {
        return new ReflectorMage(this);
    }
}

class ReflectorMageEffect extends OneShotEffect {

    public ReflectorMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature an opponent controls to its owner's hand. That creature's owner can't cast spells with the same name as that creature until your next turn";
    }

    public ReflectorMageEffect(final ReflectorMageEffect effect) {
        super(effect);
    }

    @Override
    public ReflectorMageEffect copy() {
        return new ReflectorMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                controller.moveCards(targetCreature, Zone.HAND, source, game);
                if (!targetCreature.getName().isEmpty()) { // if the creature had no name, no restrict effect will be created
                    game.addEffect(new ExclusionRitualReplacementEffect(targetCreature.getName(), targetCreature.getOwnerId()), source);
                }
            }
            return true;
        }
        return false;
    }
}

class ExclusionRitualReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    private final String creatureName;
    private final UUID ownerId;

    ExclusionRitualReplacementEffect(String creatureName, UUID ownerId) {
        super(Duration.UntilYourNextTurn, Outcome.Detriment);
        staticText = "That creature's owner can't cast spells with the same name as that creature until your next turn";
        this.creatureName = creatureName;
        this.ownerId = ownerId;
    }

    ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
        super(effect);
        this.creatureName = effect.creatureName;
        this.ownerId = effect.ownerId;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        if (card != null) {
            Spell spell = game.getState().getStack().getSpell(event.getSourceId());
            if (spell != null && spell.isFaceDown(game)) {
                return false; // Face Down cast spell (Morph creature) has no name
            }
            return card.getName().equals(creatureName) && Objects.equals(ownerId, card.getOwnerId());
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}

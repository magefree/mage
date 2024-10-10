package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ReflectorMage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
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

    private ReflectorMage(final ReflectorMage card) {
        super(card);
    }

    @Override
    public ReflectorMage copy() {
        return new ReflectorMage(this);
    }
}

class ReflectorMageEffect extends OneShotEffect {

    ReflectorMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature an opponent controls to its owner's hand. " +
                "That creature's owner can't cast spells with the same name as that creature until your next turn";
    }

    private ReflectorMageEffect(final ReflectorMageEffect effect) {
        super(effect);
    }

    @Override
    public ReflectorMageEffect copy() {
        return new ReflectorMageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetCreature == null) {
            return false;
        }
        controller.moveCards(targetCreature, Zone.HAND, source, game);
        game.addEffect(new ReflectorMageReplacementEffect(targetCreature, targetCreature.getOwnerId()), source);
        return true;
    }
}

class ReflectorMageReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    private final Permanent creature;
    private final UUID ownerId;

    ReflectorMageReplacementEffect(Permanent creature, UUID ownerId) {
        super(Duration.UntilYourNextTurn, Outcome.Detriment);
        staticText = "That creature's owner can't cast spells with the same name as that creature until your next turn";
        this.creature = creature.copy();
        this.ownerId = ownerId;
    }

    private ReflectorMageReplacementEffect(final ReflectorMageReplacementEffect effect) {
        super(effect);
        this.creature = effect.creature.copy();
        this.ownerId = effect.ownerId;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        SpellAbility spellAbility = SpellAbility.getSpellAbilityFromEvent(event, game);
        Card card = spellAbility.getCharacteristics(game);
        return spellAbility != null && card != null && card.isOwnedBy(ownerId) && card.sharesName(creature, game);
    }

    @Override
    public ReflectorMageReplacementEffect copy() {
        return new ReflectorMageReplacementEffect(this);
    }
}

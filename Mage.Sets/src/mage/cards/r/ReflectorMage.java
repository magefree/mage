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
import mage.util.CardUtil;

import java.util.Objects;
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

    public ReflectorMageEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature an opponent controls to its owner's hand. That creature's owner can't cast spells with the same name as that creature until your next turn";
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
        if (controller != null) {
            Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (targetCreature != null) {
                controller.moveCards(targetCreature, Zone.HAND, source, game);
                if (!CardUtil.haveEmptyName(targetCreature)) { // if the creature had no name, no restrict effect will be created
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

    private ExclusionRitualReplacementEffect(final ExclusionRitualReplacementEffect effect) {
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
        SpellAbility spellAbility = SpellAbility.getSpellAbilityFromEvent(event, game);
        if (spellAbility == null) {
            return false;
        }
        Card card = spellAbility.getCharacteristics(game);
        if (card == null) {
            return false;
        }
        return CardUtil.haveSameNames(card, creatureName, game) && Objects.equals(ownerId, card.getOwnerId());
    }

    @Override
    public ExclusionRitualReplacementEffect copy() {
        return new ExclusionRitualReplacementEffect(this);
    }
}

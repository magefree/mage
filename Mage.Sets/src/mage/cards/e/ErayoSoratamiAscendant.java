
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.token.TokenImpl;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class ErayoSoratamiAscendant extends CardImpl {

    public ErayoSoratamiAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.flipCard = true;
        this.flipCardName = "Erayo's Essence";

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever the fourth spell of a turn is cast, flip Erayo, Soratami Ascendant.
        this.addAbility(new ErayoSoratamiAscendantTriggeredAbility());

    }

    private ErayoSoratamiAscendant(final ErayoSoratamiAscendant card) {
        super(card);
    }

    @Override
    public ErayoSoratamiAscendant copy() {
        return new ErayoSoratamiAscendant(this);
    }
}

class ErayoSoratamiAscendantTriggeredAbility extends TriggeredAbilityImpl {

    public ErayoSoratamiAscendantTriggeredAbility() {
        super(Zone.BATTLEFIELD, getFlipEffect(), false);
        setTriggerPhrase("Whenever the fourth spell of a turn is cast, ");
    }

    private static Effect getFlipEffect() {
        Effect effect = new FlipSourceEffect(new ErayosEssenceToken());
        effect.setText("flip {this}");
        return effect;
    }

    public ErayoSoratamiAscendantTriggeredAbility(final ErayoSoratamiAscendantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsAllPlayersCastOnCurrentTurn() == 4;
    }

    @Override
    public ErayoSoratamiAscendantTriggeredAbility copy() {
        return new ErayoSoratamiAscendantTriggeredAbility(this);
    }
}

class ErayosEssenceToken extends TokenImpl {

    ErayosEssenceToken() {
        super("Erayo's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);

        color.setBlue(true);

        // Whenever an opponent casts a spell for the first time in a turn, counter that spell.
        Effect effect = new CounterTargetEffect();
        effect.setText("counter that spell");
        this.addAbility(new ErayosEssenceTriggeredAbility(effect));
    }
    public ErayosEssenceToken(final ErayosEssenceToken token) {
        super(token);
    }

    public ErayosEssenceToken copy() {
        return new ErayosEssenceToken(this);
    }
}

class ErayosEssenceTriggeredAbility extends TriggeredAbilityImpl {

    public ErayosEssenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent casts a spell for the first time each turn, ");
    }

    public ErayosEssenceTriggeredAbility(final ErayosEssenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
            CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
            if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 1) {
                for (Effect effect : getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ErayosEssenceTriggeredAbility copy() {
        return new ErayosEssenceTriggeredAbility(this);
    }
}

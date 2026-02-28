
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
import mage.game.permanent.token.custom.EnchantmentToken;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class ErayoSoratamiAscendant extends CardImpl {

    public ErayoSoratamiAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.supertype.add(SuperType.LEGENDARY);
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
        Effect tokenEffect = new CounterTargetEffect().setText("counter that spell");
        EnchantmentToken flipToken = new EnchantmentToken("Erayo's Essence", true)
            .withColor("U")
            .withAbility(new ErayosEssenceTriggeredAbility(tokenEffect));

        Effect effect = new FlipSourceEffect(flipToken);
        effect.setText("flip {this}");
        return effect;
    }

    private ErayoSoratamiAscendantTriggeredAbility(final ErayoSoratamiAscendantTriggeredAbility ability) {
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

class ErayosEssenceTriggeredAbility extends TriggeredAbilityImpl {

    public ErayosEssenceTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, false);
        setTriggerPhrase("Whenever an opponent casts a spell for the first time each turn, ");
    }

    private ErayosEssenceTriggeredAbility(final ErayosEssenceTriggeredAbility ability) {
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

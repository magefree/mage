package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Xanderhall
 */
public class KharnTheBetrayer extends CardImpl {

    public KharnTheBetrayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.ASTARTES, SubType.BERSERKER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Berzerker - Kharn the Betrayer attacks or blocks each combat if able.
        Ability ability = new SimpleStaticAbility(new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("{this} attacks"));
        ability.addEffect(new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("blocks each combat if able").concatBy("or"));
        this.addAbility(ability.withFlavorWord("Berzerker"));

        // Sigil of Corruption - When you lose control of Kharn the Betrayer, draw two cards.
        this.addAbility(new KharnTheBetrayerTriggeredAbility().withFlavorWord("Sigil of Corruption"));

        // The Betrayer - If damage would be dealt to Kharn the Betrayer, prevent that damage and an opponent of your choice gains control of it.
        this.addAbility(new SimpleStaticAbility(new KharnTheBetrayerPreventionEffect()).withFlavorWord("The Betrayer"));
    }

    private KharnTheBetrayer(final KharnTheBetrayer card) {
        super(card);
    }

    @Override
    public KharnTheBetrayer copy() {
        return new KharnTheBetrayer(this);
    }
    
}

class KharnTheBetrayerTriggeredAbility extends TriggeredAbilityImpl {

    KharnTheBetrayerTriggeredAbility () {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(2));
        this.setTriggerPhrase("When you lose control of {this}, ");
    }

    private KharnTheBetrayerTriggeredAbility(KharnTheBetrayerTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }
    
    @Override
    public KharnTheBetrayerTriggeredAbility copy() {
        return new KharnTheBetrayerTriggeredAbility(this);
    }
}

class KharnTheBetrayerPreventionEffect extends PreventionEffectImpl {

    KharnTheBetrayerPreventionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "if damage would be dealt to {this}, prevent that damage and an opponent of your choice gains control of it";
    }

    private KharnTheBetrayerPreventionEffect(final KharnTheBetrayerPreventionEffect effect) {
        super(effect);
    }

    @Override
    public KharnTheBetrayerPreventionEffect copy() {
        return new KharnTheBetrayerPreventionEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || preventDamageAction(event, source, game).getPreventedDamage() == 0) {
            return false;
        }

        TargetOpponent target = new TargetOpponent();
        if (!player.choose(outcome, target, source, game)) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, target.getFirstTarget());
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(effect, false, "an opponent of your choice gains control of it.");
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && event.getTargetId().equals(source.getSourceId());
    }

}

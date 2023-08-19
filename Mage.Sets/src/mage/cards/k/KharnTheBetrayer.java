package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;
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

        // {this} attacks or blocks each combat if able
        Ability ability = new SimpleStaticAbility(new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("{this} attacks"));
        ability.addEffect(new BlocksIfAbleSourceEffect(Duration.WhileOnBattlefield).setText("blocks each combat if able").concatBy("or"));
        this.addAbility(ability.withFlavorWord("Berzerker"));

        // When you lose control of {this}, draw two cards
        this.addAbility(new KharnTheBetrayerTriggeredAbility().withFlavorWord("Sigil of Corruption"));

        // If damage would be dealt to {this}, prevent that damage and an opponent of your choice gains control of it.
        Ability replacementAbility = new SimpleStaticAbility(new KharnTheBetrayerEffect());
        this.addAbility(replacementAbility.withFlavorWord("The Betrayer"));
    }

    protected KharnTheBetrayer(final KharnTheBetrayer card) {
        super(card);
    }

    @Override
    public Card copy() {
        return new KharnTheBetrayer(this);
    }
    
}

class KharnTheBetrayerEffect extends ReplacementEffectImpl {

    public KharnTheBetrayerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.PreventDamage);
        this.staticText = "If damage would be dealt to {this}, prevent that damage and an opponent of your choice gains control of it.";
    }

    protected KharnTheBetrayerEffect(final KharnTheBetrayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int damage = game.preventDamage(event, source, game, Integer.MAX_VALUE).getPreventedDamage();
        Player player = game.getPlayer(source.getControllerId());

        if (player != null && damage > 0) {
            TargetPlayer target = new TargetOpponent();
            target.setNotTarget(true);
            if (!player.choose(outcome, target, source, game)) {
                return false;
            }
            ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, true, target.getFirstTarget());
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
            
            ReflexiveTriggeredAbility ability 
                = new ReflexiveTriggeredAbility(effect, false, "an opponent of your choice gains control of it.");
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public ContinuousEffect copy() {
        return new KharnTheBetrayerEffect(this);
    }
    
}

class KharnTheBetrayerTriggeredAbility extends TriggeredAbilityImpl {

    KharnTheBetrayerTriggeredAbility () {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(2).setText("When you lose control of {this}, draw two cards"));
    }

    KharnTheBetrayerTriggeredAbility(KharnTheBetrayerTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(sourceId)) {
            this.getEffects().stream().findFirst().ifPresent(e -> e.setTargetPointer(new FixedTarget(event.getPlayerId())));
            return true;
        }
        return false;
    }
    
    @Override
    public KharnTheBetrayerTriggeredAbility copy() {
        return new KharnTheBetrayerTriggeredAbility(this);
    }
}
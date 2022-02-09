package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;

/**
 * @author Loki
 */
public final class AkkiLavarunner extends CardImpl {

    public AkkiLavarunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Tok-Tok, Volcano Born";

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Whenever Akki Lavarunner deals damage to an opponent, flip it.
        this.addAbility(new AkkiLavarunnerAbility());
    }

    private AkkiLavarunner(final AkkiLavarunner card) {
        super(card);
    }

    @Override
    public AkkiLavarunner copy() {
        return new AkkiLavarunner(this);
    }
}

class AkkiLavarunnerAbility extends TriggeredAbilityImpl {

    public AkkiLavarunnerAbility() {
        super(Zone.BATTLEFIELD, new FlipSourceEffect(new TokTokVolcanoBorn()));
    }

    public AkkiLavarunnerAbility(final AkkiLavarunnerAbility ability) {
        super(ability);
    }

    @Override
    public AkkiLavarunnerAbility copy() {
        return new AkkiLavarunnerAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        return damageEvent.isCombatDamage() && this.sourceId.equals(event.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to an opponent, flip it.";
    }
}

class TokTokVolcanoBorn extends TokenImpl {
    TokTokVolcanoBorn() {
        super("Tok-Tok, Volcano Born", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.GOBLIN);
        subtype.add(SubType.SHAMAN);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TokTokVolcanoBornEffect()));
    }
    public TokTokVolcanoBorn(final TokTokVolcanoBorn token) {
        super(token);
    }

    public TokTokVolcanoBorn copy() {
        return new TokTokVolcanoBorn(this);
    }
}

class TokTokVolcanoBornEffect extends ReplacementEffectImpl {

    TokTokVolcanoBornEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a red source would deal damage to a player, it deals that much damage plus 1 to that player instead";
    }

    TokTokVolcanoBornEffect(final TokTokVolcanoBornEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if(sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
        }
        else {
            sourceObject = sourcePermanent;
        }

        if (sourceObject != null && sourceObject.getColor(game).isRed()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }

    @Override
    public TokTokVolcanoBornEffect copy() {
        return new TokTokVolcanoBornEffect(this);
    }

}

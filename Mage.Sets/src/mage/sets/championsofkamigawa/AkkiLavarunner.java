package mage.sets.championsofkamigawa;

import mage.Constants;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyTokenEffect;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.token.Token;

import java.util.UUID;

/**
 * @author Loki
 */
public class AkkiLavarunner extends CardImpl<AkkiLavarunner> {

    public AkkiLavarunner(UUID ownerId) {
        super(ownerId, 153, "Akki Lavarunner", Constants.Rarity.RARE, new Constants.CardType[]{Constants.CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.flipCard = true;
        this.flipCardName = "Tok-Tok, Volcano Born";
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new AkkiLavarunnerAbility());
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ConditionalContinousEffect(new CopyTokenEffect(new TokTokVolcanoBorn()), FlippedCondition.getInstance(), "")));
    }

    public AkkiLavarunner(final AkkiLavarunner card) {
        super(card);
    }

    @Override
    public AkkiLavarunner copy() {
        return new AkkiLavarunner(this);
    }
}

class AkkiLavarunnerAbility extends TriggeredAbilityImpl<AkkiLavarunnerAbility> {

    public AkkiLavarunnerAbility() {
        super(Constants.Zone.BATTLEFIELD, new FlipSourceEffect());
    }

    public AkkiLavarunnerAbility(final AkkiLavarunnerAbility ability) {
        super(ability);
    }

    @Override
    public AkkiLavarunnerAbility copy() {
        return new AkkiLavarunnerAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
            if (damageEvent.isCombatDamage() && this.sourceId.equals(event.getSourceId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals damage to an opponent, flip it.";
    }
}

class TokTokVolcanoBorn extends Token {

    private final static FilterCard filter = new FilterCard("red");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    TokTokVolcanoBorn() {
        super("Tok-Tok, Volcano Born", "");
        supertype.add("Legendary");
        cardType.add(Constants.CardType.CREATURE);
        color.setRed(true);
        subtype.add("Goblin");
        subtype.add("Shaman");
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new TokTokVolcanoBornEffect()));
    }
}

class TokTokVolcanoBornEffect extends ReplacementEffectImpl<TokTokVolcanoBornEffect> {

    TokTokVolcanoBornEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "If a red source would deal damage to a player, it deals that much damage plus 1 to that player instead";
    }

    TokTokVolcanoBornEffect(final TokTokVolcanoBornEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getColor().isRed()) {
                event.setAmount(event.getAmount() + 1);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public TokTokVolcanoBornEffect copy() {
        return new TokTokVolcanoBornEffect(this);
    }

}

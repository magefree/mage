package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 * @author muz
 */
public final class SeleniasCurseToken extends TokenImpl {

    public SeleniasCurseToken() {
        super(
            "Selenia's Curse", "legendary black Aura Curse enchantment token named Selenia's Curse"
        );
        this.supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);
        subtype.add(SubType.AURA);
        subtype.add(SubType.CURSE);
        color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        Ability ability = new EnchantAbility(auraTarget);
        ability.addTarget(auraTarget);
        ability.addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(ability);

        // If enchanted player would lose life, they lose twice that much life instead
        this.addAbility(new SimpleStaticAbility(
            new SeleniasCurseTokenEffect()
        ));
    }

    private SeleniasCurseToken(final SeleniasCurseToken token) {
        super(token);
    }

    public SeleniasCurseToken copy() {
        return new SeleniasCurseToken(this);
    }
}

class SeleniasCurseTokenEffect extends ReplacementEffectImpl {

    public SeleniasCurseTokenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "if enchanted player would lose life, they lose twice that much life instead";
    }

    private SeleniasCurseTokenEffect(final SeleniasCurseTokenEffect effect) {
        super(effect);
    }

    @Override
    public SeleniasCurseTokenEffect copy() {
        return new SeleniasCurseTokenEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOSE_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null || !event.getPlayerId().equals(sourcePermanent.getAttachedTo())) {
            return false;
        }
        return true;
    }
}

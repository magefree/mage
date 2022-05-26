package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CommanderCastCountValue;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FontOfMagic extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Commanders cast from command zone", CommanderCastCountValue.instance
    );

    public FontOfMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Instant and sorcery spells you cast cost {1} less to cast for each time you've cast a commander from the command zone this game.
        this.addAbility(new SimpleStaticAbility(new FontOfMagicEffect()).addHint(hint));
    }

    private FontOfMagic(final FontOfMagic card) {
        super(card);
    }

    @Override
    public FontOfMagic copy() {
        return new FontOfMagic(this);
    }
}

class FontOfMagicEffect extends CostModificationEffectImpl {

    FontOfMagicEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "instant and sorcery spells you cast cost {1} less to cast " +
                "for each time you've cast a commander from the command zone this game";
    }

    private FontOfMagicEffect(final FontOfMagicEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, Math.max(0, CommanderCastCountValue.instance.calculate(game, source, this)));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.isControlledBy(source.getControllerId())
                && ((SpellAbility) abilityToModify).getCharacteristics(game).isInstantOrSorcery(game)
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public FontOfMagicEffect copy() {
        return new FontOfMagicEffect(this);
    }
}

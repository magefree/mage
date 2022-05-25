
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 *
 * @author HCrescent & L_J
 */
public final class WarTax extends CardImpl {

    public WarTax(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // {X}{U}: This turn, creatures can't attack unless their controller pays {X} for each attacking creature they control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WarTaxCantAttackUnlessPaysEffect(), new ManaCostsImpl("{X}{U}")));
    }

    private WarTax(final WarTax card) {
        super(card);
    }

    @Override
    public WarTax copy() {
        return new WarTax(this);
    }
}

class WarTaxCantAttackUnlessPaysEffect extends PayCostToAttackBlockEffectImpl {

    DynamicValue xCosts = ManacostVariableValue.REGULAR;

    WarTaxCantAttackUnlessPaysEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral, RestrictType.ATTACK);
        staticText = "This turn, creatures can't attack unless their controller pays {X} for each attacking creature they control";
    }

    WarTaxCantAttackUnlessPaysEffect(WarTaxCantAttackUnlessPaysEffect effect) {
        super(effect);
        this.xCosts = effect.xCosts.copy();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            int amount = xCosts.calculate(game, source, this);
            return new ManaCostsImpl<>("{" + amount + '}');
        }
        return null;
    }
    
    @Override
    public boolean isCostless(GameEvent event, Ability source, Game game) {
        return false;
    }

    @Override
    public WarTaxCantAttackUnlessPaysEffect copy() {
        return new WarTaxCantAttackUnlessPaysEffect(this);
    }

}

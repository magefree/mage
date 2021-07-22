
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public final class Trinisphere extends CardImpl {

    public Trinisphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrinisphereEffect()));
    }

    private Trinisphere(final Trinisphere card) {
        super(card);
    }

    @Override
    public Trinisphere copy() {
        return new Trinisphere(this);
    }
}

class TrinisphereEffect extends CostModificationEffectImpl {

    public TrinisphereEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.SET_COST);
        this.staticText = "As long as {this} is untapped, each spell that would cost less than three mana to cast costs three mana to cast";
    }

    protected TrinisphereEffect(TrinisphereEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int manaCost = abilityToModify.getManaCostsToPay().manaValue();
        if (manaCost < 3) {
            CardUtil.increaseCost(abilityToModify, 3 - manaCost);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            return permanent != null && !permanent.isTapped();
        }
        return false;
    }

    @Override
    public TrinisphereEffect copy() {
        return new TrinisphereEffect(this);
    }
}

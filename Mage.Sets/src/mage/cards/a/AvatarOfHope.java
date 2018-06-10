
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public final class AvatarOfHope extends CardImpl {

    public AvatarOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // If you have 3 or less life, Avatar of Hope costs {6} less to cast.
        this.addAbility(new AdjustingCostsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Avatar of Hope can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0)));
    }

    public AvatarOfHope(final AvatarOfHope card) {
        super(card);
    }

    @Override
    public AvatarOfHope copy() {
        return new AvatarOfHope(this);
    }
}

class AdjustingCostsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AdjustingCostsAbility() {
        super(Zone.OUTSIDE, new AdjustingCostsEffect());
    }

    public AdjustingCostsAbility(final AdjustingCostsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AdjustingCostsAbility(this);
    }

    @Override
    public String getRule() {
        return "If you have 3 or less life, {this} costs {6} less to cast";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability.getAbilityType() == AbilityType.SPELL) {
            Player player = game.getPlayer(ability.getControllerId());
            if (player != null && player.getLife() < 4) {
                CardUtil.adjustCost((SpellAbility) ability, 6);
            }
        }
    }
}

class AdjustingCostsEffect extends CostModificationEffectImpl {

    public AdjustingCostsEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
    }

    public AdjustingCostsEffect(final AdjustingCostsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        Player player = game.getPlayer(source.getControllerId());

        if (mana.getGeneric() > 0 && player != null && player.getLife() < 4) {
            int newCount = mana.getGeneric() - 6;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public AdjustingCostsEffect copy() {
        return new AdjustingCostsEffect(this);
    }
}

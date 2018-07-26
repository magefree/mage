package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author NinthWorld
 */
public final class Mothership extends CardImpl {

    public Mothership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MothershipCostModificationEffect()));
    }

    public Mothership(final Mothership card) {
        super(card);
    }

    @Override
    public Mothership copy() {
        return new Mothership(this);
    }
}

class MothershipCostModificationEffect extends CostModificationEffectImpl {

    MothershipCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast cost {2} more to cast";
    }

    MothershipCostModificationEffect(MothershipCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                Card card = game.getCard(abilityToModify.getSourceId());
                if (card != null) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MothershipCostModificationEffect copy() {
        return new MothershipCostModificationEffect(this);
    }
}
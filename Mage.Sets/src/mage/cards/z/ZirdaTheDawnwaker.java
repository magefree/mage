package mage.cards.z;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZirdaTheDawnwaker extends CardImpl {

    public ZirdaTheDawnwaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Companion — Each permanent card in your starting deck has an activated ability.
        this.addAbility(new CompanionAbility(ZirdaTheDawnwakerCompanionCondition.instance));

        // Abilities you activate that aren't mana abilities cost {2} less to activate.
        // This effect can't reduce the mana in that cost to less than one mana.
        this.addAbility(new SimpleStaticAbility(new ZirdaTheDawnwakerEffect()));

        // {1}, {T}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ZirdaTheDawnwaker(final ZirdaTheDawnwaker card) {
        super(card);
    }

    @Override
    public ZirdaTheDawnwaker copy() {
        return new ZirdaTheDawnwaker(this);
    }
}

enum ZirdaTheDawnwakerCompanionCondition implements CompanionCondition {
    instance;

    @Override
    public String getRule() {
        return "Each permanent card in your starting deck has an activated ability.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingHandSize) {
        return deck
                .stream()
                .filter(MageObject::isPermanent)
                .map(MageObject::getAbilities)
                .flatMap(Collection::stream)
                .anyMatch(ActivatedAbility.class::isInstance);
    }
}

class ZirdaTheDawnwakerEffect extends CostModificationEffectImpl {

    ZirdaTheDawnwakerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Abilities you activate that aren't mana abilities cost {2} less to activate. "
                + "This effect can't reduce the mana in that cost to less than one mana";
    }

    private ZirdaTheDawnwakerEffect(final ZirdaTheDawnwakerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reduceMax = CardUtil.calculateActualPossibleGenericManaReduction(
                abilityToModify.getManaCostsToPay().getMana(), 2, 1
        );
        if (reduceMax < 1) {
            return true;
        }
        CardUtil.reduceCost(abilityToModify, reduceMax);
        return true;

    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getAbilityType() == AbilityType.ACTIVATED
                && abilityToModify.isControlledBy(source.getControllerId());
    }

    @Override
    public ZirdaTheDawnwakerEffect copy() {
        return new ZirdaTheDawnwakerEffect(this);
    }
}

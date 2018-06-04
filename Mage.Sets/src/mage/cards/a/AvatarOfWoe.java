
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class AvatarOfWoe extends CardImpl {

    public AvatarOfWoe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}{B}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // If there are ten or more creature cards total in all graveyards, Avatar of Woe costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new AvatarOfWoeCostReductionEffect()));
        
        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // {tap}: Destroy target creature. It can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(true), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public AvatarOfWoe(final AvatarOfWoe card) {
        super(card);
    }

    @Override
    public AvatarOfWoe copy() {
        return new AvatarOfWoe(this);
    }
}

class AvatarOfWoeCostReductionEffect extends CostModificationEffectImpl {

    AvatarOfWoeCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "If there are ten or more creature cards total in all graveyards, {this} costs {6} less to cast";
    }

    AvatarOfWoeCostReductionEffect(final AvatarOfWoeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
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
        return abilityToModify.getSourceId().equals(source.getSourceId()) 
                && (abilityToModify instanceof SpellAbility) 
                && new CardsInAllGraveyardsCount(new FilterCreatureCard()).calculate(game, source, this) >= 10;
    }

    @Override
    public AvatarOfWoeCostReductionEffect copy() {
        return new AvatarOfWoeCostReductionEffect(this);
    }
}

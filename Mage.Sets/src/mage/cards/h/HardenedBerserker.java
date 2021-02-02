
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class HardenedBerserker extends CardImpl {

    public HardenedBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Hardened Berserker attacks the next spell you cast this turn costs {1} less to cast.
        this.addAbility(new AttacksTriggeredAbility(new HardenedBerserkerSpellsCostReductionEffect(), false));
    }

    private HardenedBerserker(final HardenedBerserker card) {
        super(card);
    }

    @Override
    public HardenedBerserker copy() {
        return new HardenedBerserker(this);
    }
}

class HardenedBerserkerSpellsCostReductionEffect extends CostModificationEffectImpl {

    int spellsCast;
    
    public HardenedBerserkerSpellsCostReductionEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "the next spell you cast this turn costs {1} less to cast";
    }

    protected HardenedBerserkerSpellsCostReductionEffect(final HardenedBerserkerSpellsCostReductionEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            spellsCast =  watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        if (watcher != null) {
            if (watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(source.getControllerId()) > spellsCast) {
                discard(); // only one use 
                return false;
            }
        }        
        if (abilityToModify instanceof SpellAbility) {
            return abilityToModify.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public HardenedBerserkerSpellsCostReductionEffect copy() {
        return new HardenedBerserkerSpellsCostReductionEffect(this);
    }
}


package mage.cards.b;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.Target;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class BattlefieldThaumaturge extends CardImpl {

    public BattlefieldThaumaturge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Each instant and sorcery spell you cast costs 1 less to cast for each creature it targets.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BattlefieldThaumaturgeSpellsCostReductionEffect()));
        // Heroic - Whenever you cast a spell that targets Battlefield Thaumaturge, Battlefield Thaumaturge gains hexproof until end of turn.
        this.addAbility(new HeroicAbility(new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.EndOfTurn)));
    }

    public BattlefieldThaumaturge(final BattlefieldThaumaturge card) {
        super(card);
    }

    @Override
    public BattlefieldThaumaturge copy() {
        return new BattlefieldThaumaturge(this);
    }
}

class BattlefieldThaumaturgeSpellsCostReductionEffect extends CostModificationEffectImpl {

    public BattlefieldThaumaturgeSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Each instant and sorcery spell you cast costs {1} less to cast for each creature it targets";
    }

    protected BattlefieldThaumaturgeSpellsCostReductionEffect(BattlefieldThaumaturgeSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Set<UUID> creaturesTargeted = new HashSet<>();
        for (Target target : abilityToModify.getTargets()) {
            for (UUID uuid : target.getTargets()) {
                Permanent permanent = game.getPermanent(uuid);
                if (permanent != null && permanent.isCreature()) {
                    creaturesTargeted.add(permanent.getId());
                }
            }
        }
        CardUtil.reduceCost(abilityToModify, creaturesTargeted.size());
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility)
                && abilityToModify.isControlledBy(source.getControllerId())) {
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            return spell != null && StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY.match(spell, game);
        }
        return false;
    }

    @Override
    public BattlefieldThaumaturgeSpellsCostReductionEffect copy() {
        return new BattlefieldThaumaturgeSpellsCostReductionEffect(this);
    }
}

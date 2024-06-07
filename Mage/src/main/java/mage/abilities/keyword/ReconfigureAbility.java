package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public class ReconfigureAbility extends ActivatedAbilityImpl {

    private final Cost cost;

    public ReconfigureAbility(String manaString) {
        this(new ManaCostsImpl<>(manaString));
    }

    public ReconfigureAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature), cost);
        this.cost = cost;
        this.timing = TimingRule.SORCERY;
        this.addTarget(new TargetControlledCreaturePermanent());
        this.addSubAbility(new ReconfigureUnattachAbility(cost));
        Ability ability = new SimpleStaticAbility(new ReconfigureTypeEffect());
        ability.setRuleVisible(false);
        this.addSubAbility(ability);
    }

    private ReconfigureAbility(final ReconfigureAbility ability) {
        super(ability);
        this.cost = ability.cost;
    }

    @Override
    public ReconfigureAbility copy() {
        return new ReconfigureAbility(this);
    }

    @Override
    public String getRule() {
        return "Reconfigure " + cost.getText() + " (" + cost.getText()
                + ": Attach to target creature you control; " +
                "or unattach from a creature. Reconfigure only as a sorcery. " +
                "While attached, this isn't a creature.)";
    }
}

class ReconfigureUnattachAbility extends ActivatedAbilityImpl {

    protected ReconfigureUnattachAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new ReconfigureUnattachEffect(), cost);
        this.condition = ReconfigureUnattachAbility::checkForCreature;
        this.timing = TimingRule.SORCERY;
        this.setRuleVisible(false);
    }

    private ReconfigureUnattachAbility(final ReconfigureUnattachAbility ability) {
        super(ability);
    }

    @Override
    public ReconfigureUnattachAbility copy() {
        return new ReconfigureUnattachAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only if this permanent is attached to a creature and only as a sorcery.";
    }

    private static boolean checkForCreature(Game game, Ability source) {
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        if (equipment == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(equipment.getAttachedTo());
        return permanent != null && permanent.isCreature(game);
    }
}

class ReconfigureUnattachEffect extends OneShotEffect {

    ReconfigureUnattachEffect() {
        super(Outcome.Benefit);
        staticText = "unattach this permanent";
    }

    private ReconfigureUnattachEffect(final ReconfigureUnattachEffect effect) {
        super(effect);
    }

    @Override
    public ReconfigureUnattachEffect copy() {
        return new ReconfigureUnattachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.unattach(game);
        }
        return true;
    }
}

class ReconfigureTypeEffect extends ContinuousEffectImpl {

    ReconfigureTypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
    }

    private ReconfigureTypeEffect(final ReconfigureTypeEffect effect) {
        super(effect);
    }

    @Override
    public ReconfigureTypeEffect copy() {
        return new ReconfigureTypeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || game.getPermanent(permanent.getAttachedTo()) == null) {
            return false;
        }
        permanent.removeCardType(game, CardType.CREATURE);
        return true;
    }
}

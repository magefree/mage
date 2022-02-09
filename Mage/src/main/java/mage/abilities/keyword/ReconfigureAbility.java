package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
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

    private final String manaString;

    public ReconfigureAbility(String manaString) {
        super(Zone.BATTLEFIELD, new AttachEffect(Outcome.BoostCreature), new ManaCostsImpl<>(manaString));
        this.manaString = manaString;
        this.timing = TimingRule.SORCERY;
        this.addTarget(new TargetControlledCreaturePermanent());
        this.addSubAbility(new ReconfigureUnattachAbility(manaString));
        Ability ability = new SimpleStaticAbility(new ReconfigureTypeEffect());
        ability.setRuleVisible(false);
        this.addSubAbility(ability);
    }

    private ReconfigureAbility(final ReconfigureAbility ability) {
        super(ability);
        this.manaString = ability.manaString;
    }

    @Override
    public ReconfigureAbility copy() {
        return new ReconfigureAbility(this);
    }

    @Override
    public String getRule() {
        return "Reconfigure " + manaString + " (" + manaString
                + ": Attach to target creature you control; " +
                "or unattach from a creature. Reconfigure only as a sorcery. " +
                "While attached, this isn't a creature.)";
    }
}

class ReconfigureUnattachAbility extends ActivateAsSorceryActivatedAbility {

    protected ReconfigureUnattachAbility(String manaString) {
        super(Zone.BATTLEFIELD, new ReconfigureUnattachEffect(), new ManaCostsImpl<>(manaString));
        this.setRuleVisible(false);
    }

    private ReconfigureUnattachAbility(final ReconfigureUnattachAbility ability) {
        super(ability);
    }

    @Override
    public ReconfigureUnattachAbility copy() {
        return new ReconfigureUnattachAbility(this);
    }
}

class ReconfigureUnattachEffect extends OneShotEffect {

    ReconfigureUnattachEffect() {
        super(Outcome.Benefit);
        staticText = "unattach {this}";
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

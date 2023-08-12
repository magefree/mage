package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenSubstitution extends CardImpl {

    public SuddenSubstitution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Exchange control of target noncreature spell and target creature. Then the spell's controller may choose new targets for it.
        this.getSpellAbility().addEffect(new SuddenSubstitutionEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SuddenSubstitution(final SuddenSubstitution card) {
        super(card);
    }

    @Override
    public SuddenSubstitution copy() {
        return new SuddenSubstitution(this);
    }
}

class SuddenSubstitutionEffect extends OneShotEffect {

    SuddenSubstitutionEffect() {
        super(Outcome.Benefit);
        staticText = "Exchange control of target noncreature spell and target creature. " +
                "Then the spell's controller may choose new targets for it.";
    }

    private SuddenSubstitutionEffect(final SuddenSubstitutionEffect effect) {
        super(effect);
    }

    @Override
    public SuddenSubstitutionEffect copy() {
        return new SuddenSubstitutionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getTargets().get(0).getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (spell == null || creature == null) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, spell.getControllerId());
        effect.setTargetPointer(new FixedTarget(creature, game));
        spell.setControllerId(creature.getControllerId());
        spell.chooseNewTargets(game, creature.getControllerId());
        game.addEffect(effect, source);
        return true;
    }
}

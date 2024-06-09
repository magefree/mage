package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CantBeCopiedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisplayOfPower extends CardImpl {

    public DisplayOfPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{R}");

        // This spell can't be copied.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCopiedSourceEffect()).setRuleAtTheTop(true));

        // Copy any number of target instant and/or sorcery spells. You may choose new targets for the copies.
        this.getSpellAbility().addEffect(new DisplayOfPowerEffect());
        this.getSpellAbility().addTarget(new TargetSpell(
                0, Integer.MAX_VALUE,
                StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY
        ));
    }

    private DisplayOfPower(final DisplayOfPower card) {
        super(card);
    }

    @Override
    public DisplayOfPower copy() {
        return new DisplayOfPower(this);
    }
}

class DisplayOfPowerEffect extends OneShotEffect {

    DisplayOfPowerEffect() {
        super(Outcome.Benefit);
        staticText = "copy any number of target instant and/or sorcery spells. You may choose new targets for the copies";
    }

    private DisplayOfPowerEffect(final DisplayOfPowerEffect effect) {
        super(effect);
    }

    @Override
    public DisplayOfPowerEffect copy() {
        return new DisplayOfPowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Spell spell = game.getSpell(targetId);
            if (spell != null) {
                spell.createCopyOnStack(game, source, source.getControllerId(), true);
            }
        }
        return true;
    }
}

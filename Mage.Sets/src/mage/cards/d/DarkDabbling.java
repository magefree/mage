
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DarkDabbling extends CardImpl {

    public DarkDabbling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Regenerate target creature. Draw a card.
        this.getSpellAbility().addEffect(new RegenerateTargetEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, also regenerate each other creature you control.
        this.getSpellAbility().addEffect(new DarkDabblingEffect());
    }

    private DarkDabbling(final DarkDabbling card) {
        super(card);
    }

    @Override
    public DarkDabbling copy() {
        return new DarkDabbling(this);
    }
}

class DarkDabblingEffect extends OneShotEffect {

    public DarkDabblingEffect() {
        super(Outcome.Benefit);
        this.staticText = "<br><i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, also regenerate each other creature you control";
    }

    private DarkDabblingEffect(final DarkDabblingEffect effect) {
        super(effect);
    }

    @Override
    public DarkDabblingEffect copy() {
        return new DarkDabblingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (SpellMasteryCondition.instance.apply(game, source)) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                if (!permanent.getId().equals(getTargetPointer().getFirst(game, source))) {
                    RegenerateTargetEffect regenEffect = new RegenerateTargetEffect();
                    regenEffect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(regenEffect, source);
                }
            }
        }
        return true;
    }
}

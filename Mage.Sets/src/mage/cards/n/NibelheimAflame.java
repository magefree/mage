package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.condition.common.CastFromGraveyardSourceCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NibelheimAflame extends CardImpl {

    public NibelheimAflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}{R}");

        // Choose target creature you control. It deals damage equal to its power to each other creature. If this spell was cast from a graveyard, discard your hand and draw four cards.
        this.getSpellAbility().addEffect(new NibelheimAflameEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DiscardHandControllerEffect(), CastFromGraveyardSourceCondition.instance,
                "If this spell was cast from a graveyard, discard your hand and draw four cards"
        ).addEffect(new DrawCardSourceControllerEffect(4)));

        // Flashback {5}{R}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}{R}")));
    }

    private NibelheimAflame(final NibelheimAflame card) {
        super(card);
    }

    @Override
    public NibelheimAflame copy() {
        return new NibelheimAflame(this);
    }
}

class NibelheimAflameEffect extends OneShotEffect {

    NibelheimAflameEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature you control. It deals damage equal to its power to each other creature";
    }

    private NibelheimAflameEffect(final NibelheimAflameEffect effect) {
        super(effect);
    }

    @Override
    public NibelheimAflameEffect copy() {
        return new NibelheimAflameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_ANOTHER_CREATURE, source.getControllerId(), source, game
        )) {
            if(creature.equals(permanent)){
                continue;
            }
            creature.damage(power, permanent.getId(), source, game);
        }
        return true;
    }
}

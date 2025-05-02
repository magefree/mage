package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RustlerRampage extends CardImpl {

    public RustlerRampage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Untap all creatures target player controls.
        this.getSpellAbility().addEffect(new RustlerRampageEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {1} -- Target creature gains double strike until end of turn.
        this.getSpellAbility().addMode(new Mode(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance()))
                .addTarget(new TargetCreaturePermanent())
                .withCost(new GenericManaCost(1)));
    }

    private RustlerRampage(final RustlerRampage card) {
        super(card);
    }

    @Override
    public RustlerRampage copy() {
        return new RustlerRampage(this);
    }
}

class RustlerRampageEffect extends OneShotEffect {

    RustlerRampageEffect() {
        super(Outcome.Benefit);
        staticText = "untap all creatures target player controls";
    }

    private RustlerRampageEffect(final RustlerRampageEffect effect) {
        super(effect);
    }

    @Override
    public RustlerRampageEffect copy() {
        return new RustlerRampageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                getTargetPointer().getFirst(game, source), source, game
        )) {
            permanent.untap(game);
        }
        return true;
    }
}

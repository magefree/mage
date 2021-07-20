package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeodeRager extends CardImpl {

    public GeodeRager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Landfall — Whenever a land enters the battlefield under your control, goad each creature target player controls.
        Ability ability = new LandfallAbility(new GeodeRagerEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private GeodeRager(final GeodeRager card) {
        super(card);
    }

    @Override
    public GeodeRager copy() {
        return new GeodeRager(this);
    }
}

class GeodeRagerEffect extends OneShotEffect {

    GeodeRagerEffect() {
        super(Outcome.Benefit);
        staticText = "goad each creature target player controls";
    }

    private GeodeRagerEffect(final GeodeRagerEffect effect) {
        super(effect);
    }

    @Override
    public GeodeRagerEffect copy() {
        return new GeodeRagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getFirstTarget(), source.getSourceId(), game
        )) {
            if (permanent == null) {
                continue;
            }
            game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}

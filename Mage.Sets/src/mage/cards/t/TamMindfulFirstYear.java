package mage.cards.t;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.keyword.HexproofBaseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TamMindfulFirstYear extends CardImpl {

    public TamMindfulFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each other creature you control has hexproof from each of its colors.
        this.addAbility(new SimpleStaticAbility(new TamMindfulFirstYearEffect()));

        // {T}: Target creature you control becomes all colors until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BecomesColorTargetEffect(new ObjectColor("WUBRG"), Duration.EndOfTurn)
                        .setText("target creature you control becomes all colors until end of turn"),
                new TapSourceCost()
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private TamMindfulFirstYear(final TamMindfulFirstYear card) {
        super(card);
    }

    @Override
    public TamMindfulFirstYear copy() {
        return new TamMindfulFirstYear(this);
    }
}

class TamMindfulFirstYearEffect extends ContinuousEffectImpl {

    TamMindfulFirstYearEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "each other creature you control has hexproof from each of its colors";
    }

    private TamMindfulFirstYearEffect(final TamMindfulFirstYearEffect effect) {
        super(effect);
    }

    @Override
    public TamMindfulFirstYearEffect copy() {
        return new TamMindfulFirstYearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        )) {
            ObjectColor color = permanent.getColor(game);
            for (Ability ability : HexproofBaseAbility.getFromColor(color)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}

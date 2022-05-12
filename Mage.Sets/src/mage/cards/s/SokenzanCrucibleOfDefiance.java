package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.LegendaryCreatureCostAdjuster;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.SpiritToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokenzanCrucibleOfDefiance extends CardImpl {

    public SokenzanCrucibleOfDefiance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // Channel — {3}{R}, Discard Sokenzan, Crucible of Defiance: Create two colorless 1/1 Spirit creature tokens. They gain haste until end of turn. This ability costs {1} less to activate for each legendary creature you control.
        Ability ability = new ChannelAbility("{3}{R}", new SokenzanCrucibleOfDefianceEffect());
        ability.setCostAdjuster(LegendaryCreatureCostAdjuster.instance);
        this.addAbility(ability.addHint(LegendaryCreatureCostAdjuster.getHint()));
    }

    private SokenzanCrucibleOfDefiance(final SokenzanCrucibleOfDefiance card) {
        super(card);
    }

    @Override
    public SokenzanCrucibleOfDefiance copy() {
        return new SokenzanCrucibleOfDefiance(this);
    }
}

class SokenzanCrucibleOfDefianceEffect extends OneShotEffect {

    SokenzanCrucibleOfDefianceEffect() {
        super(Outcome.Benefit);
        staticText = "create two 1/1 colorless Spirit creature tokens. They gain haste until end of turn. " +
                "This ability costs {1} less to activate for each legendary creature you control";
    }

    private SokenzanCrucibleOfDefianceEffect(final SokenzanCrucibleOfDefianceEffect effect) {
        super(effect);
    }

    @Override
    public SokenzanCrucibleOfDefianceEffect copy() {
        return new SokenzanCrucibleOfDefianceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new SpiritToken();
        token.putOntoBattlefield(2, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}

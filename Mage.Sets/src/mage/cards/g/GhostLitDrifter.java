package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GhostLitDrifter extends CardImpl {

    public GhostLitDrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{U}: Another target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                "another target creature gains flying until end of turn"
        ), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);

        // Channel — {X}{U}, Discard Ghost-Lit Drifter: X target creatures gain flying until end of turn.
        ability = new ChannelAbility("{X}{U}", new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                "X target creatures gain flying until end of turn"
        ));
        ability.setTargetAdjuster(GhostLitDrifterAdjuster.instance);
        this.addAbility(ability);
    }

    private GhostLitDrifter(final GhostLitDrifter card) {
        super(card);
    }

    @Override
    public GhostLitDrifter copy() {
        return new GhostLitDrifter(this);
    }
}

enum GhostLitDrifterAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }
}

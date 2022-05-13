package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.RhinoWarriorToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitanOfIndustry extends CardImpl {

    public TitanOfIndustry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Titan of Industry enters the battlefield, choose two —
        // • Destroy target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        ability.getModes().setMinModes(2);
        ability.getModes().setMaxModes(2);

        // • Target player gains 5 life.
        ability.addMode(new Mode(new GainLifeTargetEffect(5)).addTarget(new TargetPlayer()));

        // • Create a 4/4 green Rhino Warrior creature token.
        ability.addMode(new Mode(new CreateTokenEffect(new RhinoWarriorToken())));

        // • Put a shield counter on a creature you control.
        ability.addMode(new Mode(new TitanOfIndustryEffect()));
        this.addAbility(ability);
    }

    private TitanOfIndustry(final TitanOfIndustry card) {
        super(card);
    }

    @Override
    public TitanOfIndustry copy() {
        return new TitanOfIndustry(this);
    }
}

class TitanOfIndustryEffect extends OneShotEffect {

    TitanOfIndustryEffect() {
        super(Outcome.Benefit);
        staticText = "put a shield counter on a creature you control";
    }

    private TitanOfIndustryEffect(final TitanOfIndustryEffect effect) {
        super(effect);
    }

    @Override
    public TitanOfIndustryEffect copy() {
        return new TitanOfIndustryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.SHIELD.createInstance(), source, game);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetSacrifice;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ShattergangBrothers extends CardImpl {

    public ShattergangBrothers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{B}, Sacrifice a creature: Each other player sacrifices a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(StaticFilters.FILTER_PERMANENT_CREATURE), new ManaCostsImpl<>("{2}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);
        // {2}{R}, Sacrifice an artifact: Each other player sacrifices an artifact.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT));
        this.addAbility(ability);
        // {2}{G}, Sacrifice an enchantment: Each other player sacrifices an enchantment.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ShattergangBrothersEffect(StaticFilters.FILTER_PERMANENT_ENCHANTMENT), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
        this.addAbility(ability);
    }

    private ShattergangBrothers(final ShattergangBrothers card) {
        super(card);
    }

    @Override
    public ShattergangBrothers copy() {
        return new ShattergangBrothers(this);
    }
}

class ShattergangBrothersEffect extends OneShotEffect {

    private final FilterPermanent filter;

    ShattergangBrothersEffect(FilterPermanent filter) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.staticText = "Each other player sacrifices " + CardUtil.addArticle(filter.getMessage());
    }

    private ShattergangBrothersEffect(final ShattergangBrothersEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public ShattergangBrothersEffect copy() {
        return new ShattergangBrothersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (!Objects.equals(playerId, source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        TargetSacrifice target = new TargetSacrifice(filter);
                        if (target.canChoose(playerId, source, game)
                                && player.chooseTarget(outcome, target, source, game)) {
                            Permanent permanent = game.getPermanent(target.getFirstTarget());
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

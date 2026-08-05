package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author muz
 */
public final class StardewValley extends CardImpl {

    public StardewValley(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Tap an untapped creature you control: Create a Food token.
        Ability ability = new SimpleActivatedAbility(
            new CreateTokenEffect(new FoodToken()), new ManaCostsImpl<>("{2}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
        this.addAbility(ability);

        // {3}, {T}: Choose target permanent you control. Draw a card, then another player of your choice may gain control of that permanent. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
            new StardewValleyEffect(),
            new ManaCostsImpl<>("{3}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private StardewValley(final StardewValley card) {
        super(card);
    }

    @Override
    public StardewValley copy() {
        return new StardewValley(this);
    }
}

class StardewValleyEffect extends OneShotEffect {

    private static final FilterPlayer filter = new FilterPlayer("another player");

    static {
        filter.add(TargetController.NOT_YOU.getPlayerPredicate());
    }

    StardewValleyEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target permanent you control. Draw a card, then another player of your choice may gain control of that permanent";
    }

    private StardewValleyEffect(final StardewValleyEffect effect) {
        super(effect);
    }

    @Override
    public StardewValleyEffect copy() {
        return new StardewValleyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        TargetPlayer target = new TargetPlayer(filter);
        target.withNotTarget(true);
        if (player == null || permanent == null || !player.choose(outcome, target, source, game)) {
            return false;
        }
        player.drawCards(1, source, game);
        Player chosen = game.getPlayer(target.getFirstTarget());
        if (chosen != null && chosen.chooseUse(
            Outcome.GainControl, "Gain control of " + permanent.getLogName() + "?", source, game
        )) {
            game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, chosen.getId()
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }

        return true;
    }
}

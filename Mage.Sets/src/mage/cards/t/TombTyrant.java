package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TombTyrant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ZOMBIE, "Zombies");
    private static final FilterCard filter2 = new FilterCreatureCard();

    static {
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    private static final Condition condition = new CompoundCondition(
            "during your turn and only if there are at least three Zombie creature cards in your graveyard",
            MyTurnCondition.instance, new CardsInControllerGraveyardCondition(3, filter2)
    );
    private static final Hint hint = new ValueHint(
            "Zombie creatures in your graveyard", new CardsInControllerGraveyardCount(filter2)
    );

    public TombTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Zombies you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));

        // {2}{B}, {T}, Sacrifice a creature: Return a Zombie creature card at random from your graveyard to the battlefield. Activate only during your turn and only if there are at least three Zombie creature cards in your graveyard.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TombTyrantEffect(),
                new ManaCostsImpl<>("{2}{B}"), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability.addHint(MyTurnHint.instance).addHint(hint));
    }

    private TombTyrant(final TombTyrant card) {
        super(card);
    }

    @Override
    public TombTyrant copy() {
        return new TombTyrant(this);
    }
}

class TombTyrantEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    TombTyrantEffect() {
        super(Outcome.Benefit);
        staticText = "return a Zombie creature card at random from your graveyard to the battlefield";
    }

    private TombTyrantEffect(final TombTyrantEffect effect) {
        super(effect);
    }

    @Override
    public TombTyrantEffect copy() {
        return new TombTyrantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = RandomUtil.randomFromCollection(player.getGraveyard().getCards(filter, game));
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

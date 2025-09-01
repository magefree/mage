package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JhoiraOfTheGhitu extends CardImpl {

    public JhoiraOfTheGhitu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}, Exile a nonland card from your hand: Put four time counters on the exiled card. If it doesn't have suspend, it gains suspend.
        Ability ability = new SimpleActivatedAbility(new JhoiraOfTheGhituSuspendEffect(), new GenericManaCost(2));
        ability.addCost(new ExileFromHandCost(new TargetCardInHand(new FilterNonlandCard("a nonland card from your hand"))));
        this.addAbility(ability);
    }

    private JhoiraOfTheGhitu(final JhoiraOfTheGhitu card) {
        super(card);
    }

    @Override
    public JhoiraOfTheGhitu copy() {
        return new JhoiraOfTheGhitu(this);
    }
}

class JhoiraOfTheGhituSuspendEffect extends OneShotEffect {

    JhoiraOfTheGhituSuspendEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put four time counters on the exiled card. If it doesn't have suspend, " +
                "it gains suspend. <i>(At the beginning of your upkeep, remove a time counter from that card. " +
                "When the last is removed, cast it without paying its mana cost. If it's a creature, it has haste.)</i>";
    }

    private JhoiraOfTheGhituSuspendEffect(final JhoiraOfTheGhituSuspendEffect effect) {
        super(effect);
    }

    @Override
    public JhoiraOfTheGhituSuspendEffect copy() {
        return new JhoiraOfTheGhituSuspendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return SuspendAbility.addTimeCountersAndSuspend(
                CardUtil.castStream(source.getCosts(), ExileFromHandCost.class)
                        .map(ExileFromHandCost::getCards)
                        .flatMap(Collection::stream)
                        .findFirst()
                        .orElse(null),
                4, source, game
        );
    }
}

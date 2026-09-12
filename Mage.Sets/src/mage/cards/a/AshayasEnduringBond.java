package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.LegendarySpellAbility;
import mage.abilities.dynamicvalue.common.ManaSpentToCastCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;

/**
 * @author Meowcelina
 */
public final class AshayasEnduringBond extends CardImpl {

    public AshayasEnduringBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // (You may cast a legendary sorcery only if you control a legendary creature or planeswalker.)
        this.addAbility(new LegendarySpellAbility());

        // Discover X, where X is the amount of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new AshayasEnduringBondEffect());

        // Ashaya's Enduring Bond can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private AshayasEnduringBond(final AshayasEnduringBond card) {
        super(card);
    }

    @Override
    public AshayasEnduringBond copy() {
        return new AshayasEnduringBond(this);
    }
}

class AshayasEnduringBondEffect extends OneShotEffect {
    public AshayasEnduringBondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Discover X, where X is the amount of mana spent to cast this spell. <i>(Exile cards from the top of your library until you exile a nonland card with that mana value or less. Cast it without paying its mana cost or put it into your hand. Put the rest on the bottom in a random order.)</i>";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Player player = game.getPlayer(source.getControllerId());
        int xValue = ManaSpentToCastCount.instance.calculate(game, source, this);

        return DiscoverEffect.doDiscover(player, xValue, game, source) != null;
    }

    @Override
    public OneShotEffect copy() {
        return new AshayasEnduringBondEffect();
    }
}

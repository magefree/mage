package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAtLeastOneSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelNinjaDestroyer extends CardImpl {

    public RaphaelNinjaDestroyer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Raphael must be blocked if able.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAtLeastOneSourceEffect(Duration.WhileOnBattlefield)));

        // Enrage -- Whenever Raphael is dealt damage, add that much {R}. Until end of turn, you don't lose this mana as steps and phases end.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new RaphaelNinjaDestroyerEffect(), false, true
        ));
    }

    private RaphaelNinjaDestroyer(final RaphaelNinjaDestroyer card) {
        super(card);
    }

    @Override
    public RaphaelNinjaDestroyer copy() {
        return new RaphaelNinjaDestroyer(this);
    }
}

class RaphaelNinjaDestroyerEffect extends OneShotEffect {

    RaphaelNinjaDestroyerEffect() {
        super(Outcome.Benefit);
        staticText = "add that much {R}. Until end of turn, you don't lose this mana as steps and phases end";
    }

    private RaphaelNinjaDestroyerEffect(final RaphaelNinjaDestroyerEffect effect) {
        super(effect);
    }

    @Override
    public RaphaelNinjaDestroyerEffect copy() {
        return new RaphaelNinjaDestroyerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = SavedDamageValue.AMOUNT.calculate(game, source, this);
        if (player == null || count < 1) {
            return false;
        }
        player.getManaPool().addMana(Mana.RedMana(count), game, source, true);
        return true;
    }
}

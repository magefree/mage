
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 * @author nantuko
 */
public final class JaceMemoryAdept extends CardImpl {

    public JaceMemoryAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{3}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(4);

        // +1: Draw a card. Target player puts the top card of their library into their graveyard.
        LoyaltyAbility ability1 = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        ability1.addEffect(new PutLibraryIntoGraveTargetEffect(1));
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // 0: Target player puts the top ten cards of their library into their graveyard.
        LoyaltyAbility ability2 = new LoyaltyAbility(new PutLibraryIntoGraveTargetEffect(10), 0);
        ability2.addTarget(new TargetPlayer());
        this.addAbility(ability2);

        // -7: Any number of target players each draw twenty cards.
        LoyaltyAbility ability3 = new LoyaltyAbility(new JaceMemoryAdeptEffect(20), -7);
        ability3.addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false)); //any number
        this.addAbility(ability3);
    }

    private JaceMemoryAdept(final JaceMemoryAdept card) {
        super(card);
    }

    @Override
    public JaceMemoryAdept copy() {
        return new JaceMemoryAdept(this);
    }
}

class JaceMemoryAdeptEffect extends DrawCardTargetEffect {

    public JaceMemoryAdeptEffect(int amount) {
        super(amount);
        staticText = "Any number of target players each draw twenty cards";
    }

    private JaceMemoryAdeptEffect(final DrawCardTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID target : targetPointer.getTargets(game, source)) {
            Player player = game.getPlayer(target);
            if (player != null) {
                player.drawCards(amount.calculate(game, source, this), source, game);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return staticText;
    }

    @Override
    public JaceMemoryAdeptEffect copy() {
        return new JaceMemoryAdeptEffect(this);
    }
}

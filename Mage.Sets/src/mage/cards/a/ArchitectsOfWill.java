
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class ArchitectsOfWill extends CardImpl {

    public ArchitectsOfWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Architects of Will enters the battlefield, look at the top three cards of target player's library, then put them back in any order.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ArchitectsOfWillEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Cycling {UB}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{U/B}")));
    }

    private ArchitectsOfWill(final ArchitectsOfWill card) {
        super(card);
    }

    @Override
    public ArchitectsOfWill copy() {
        return new ArchitectsOfWill(this);
    }
}

class ArchitectsOfWillEffect extends OneShotEffect {

    public ArchitectsOfWillEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of target player's library, then put them back in any order";
    }

    public ArchitectsOfWillEffect(final ArchitectsOfWillEffect effect) {
        super(effect);
    }

    @Override
    public ArchitectsOfWillEffect copy() {
        return new ArchitectsOfWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(targetPlayer.getLibrary().getTopCards(game, 3));
        controller.lookAtCards(source, null, cards, game);
        controller.putCardsOnTopOfLibrary(cards, game, source, true);
        return true;
    }
}

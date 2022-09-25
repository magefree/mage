package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class SmugglersBuggy extends CardImpl {

    public SmugglersBuggy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.addSubType(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Hideaway 4
        //      (When this artifact enters the battlefield, look at the top four cards of your library,
        //      exile one face down, then put the rest on the bottom in a random order.)
        this.addAbility(new HideawayAbility(4));

        // Whenever Smuggler’s Buggy deals combat damage to a player, you may cast the exiled card without paying its mana cost.
        // If you do, return Smuggler’s Buggy to its owner’s hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SmugglersBuggyCastAndReturnEffect(), true));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private SmugglersBuggy(final SmugglersBuggy card) {
        super(card);
    }

    @Override
    public SmugglersBuggy copy() {
        return new SmugglersBuggy(this);
    }
}

class SmugglersBuggyCastAndReturnEffect extends OneShotEffect {

    SmugglersBuggyCastAndReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may cast the exiled card without paying its mana cost. " +
                          "If you do, return Smuggler's Buggy to its owner's hand";
    }

    private SmugglersBuggyCastAndReturnEffect(final SmugglersBuggyCastAndReturnEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent smugglersBuggy = game.getPermanent(source.getSourceId());
        if (controller == null || smugglersBuggy == null) {
            return false;
        }

        Effect hideawayPlayEffect = new HideawayPlayEffect();
        if (!hideawayPlayEffect.apply(game, source)) {
            return false;
        }

        Effect returnToHandEffect = new ReturnToHandSourceEffect();
        return returnToHandEffect.apply(game, source);
    }

    @Override
    public Effect copy() {
        return new SmugglersBuggyCastAndReturnEffect(this);
    }
}
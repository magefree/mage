package mage.cards.s;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.token.VampireLifelinkToken;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author weirddan455
 */
public final class SorinTheMirthless extends CardImpl {

    public SorinTheMirthless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SORIN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Look at the top card of your library. You may reveal that card and put it into your hand. If you do, you lose life equal to its mana value.
        this.addAbility(new LoyaltyAbility(new SorinTheMirthlessEffect(), 1));

        // −2: Create a 2/3 black Vampire creature token with flying and lifelink.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new VampireLifelinkToken()), -2));

        // −7: Sorin the Mirthless deals 13 damage to any target. You gain 13 life.
        Ability ability = new LoyaltyAbility(new DamageTargetEffect(13), -7);
        ability.addEffect(new GainLifeEffect(13));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SorinTheMirthless(final SorinTheMirthless card) {
        super(card);
    }

    @Override
    public SorinTheMirthless copy() {
        return new SorinTheMirthless(this);
    }
}

class SorinTheMirthlessEffect extends OneShotEffect {

    public SorinTheMirthlessEffect() {
        super(Outcome.DrawCard);
        staticText = "Look at the top card of your library. You may reveal that card and put it into your hand. If you do, you lose life equal to its mana value";
    }

    private SorinTheMirthlessEffect(final SorinTheMirthlessEffect effect) {
        super(effect);
    }

    @Override
    public SorinTheMirthlessEffect copy() {
        return new SorinTheMirthlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard != null) {
            Cards cards = new CardsImpl(topCard);
            controller.lookAtCards(source, null, cards, game);
            if (controller.chooseUse(outcome, "Reveal " + topCard.getName() + " and put it into your hand?", source, game)) {
                controller.revealCards(source, cards, game);
                controller.moveCards(cards, Zone.HAND, source, game);
                controller.loseLife(topCard.getManaValue(), game, source, false);
            }
        }
        return true;
    }
}

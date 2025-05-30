package mage.cards.p;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author grimreap124
 */
public final class Powerbalance extends CardImpl {

    public Powerbalance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{R}");
        

        // Whenever an opponent casts a spell, you may reveal the top card of your library. If you do, you may cast that card without paying its mana cost if the two spells have the same mana value.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new PowerbalanceEffect(), StaticFilters.FILTER_SPELL_A, true, SetTargetPointer.SPELL));
    }

    private Powerbalance(final Powerbalance card) {
        super(card);
    }

    @Override
    public Powerbalance copy() {
        return new Powerbalance(this);
    }
}

class PowerbalanceEffect extends OneShotEffect {

    PowerbalanceEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may reveal the top card of your library. If you do, you may cast that card without paying its mana cost if the two spells have the same mana value";
    }

    private PowerbalanceEffect(final PowerbalanceEffect effect) {
        super(effect);
    }

    @Override
    public PowerbalanceEffect copy() {
        return new PowerbalanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Spell spell = (Spell) game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                Card topcard = controller.getLibrary().getFromTop(game);
                if (topcard != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(topcard);
                    controller.revealCards(sourcePermanent.getName(), cards, game);
                    if (topcard.getManaValue() == spell.getManaValue()) {
                        return CardUtil.castSpellWithAttributesForFree(controller, source, game, topcard);
                    }
                }
                return true;
            }
        }
        return false;
    }


}
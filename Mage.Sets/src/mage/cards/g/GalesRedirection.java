package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalesRedirection extends CardImpl {

    public GalesRedirection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Exile target spell, then roll a d20 and add that spell's mana value.
        // 1-14 | You may cast the exiled card for as long as it remains exiled, and you may spend mana as though it were mana of any color to cast that spell.
        // 15+ | You may cast the exiled card without paying its mana cost for as long as it remains exiled.
        this.getSpellAbility().addEffect(new GalesRedirectionEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private GalesRedirection(final GalesRedirection card) {
        super(card);
    }

    @Override
    public GalesRedirection copy() {
        return new GalesRedirection(this);
    }
}

class GalesRedirectionEffect extends RollDieWithResultTableEffect {

    GalesRedirectionEffect() {
        super(20, "exile target spell, then roll a d20 and add that spell's mana value");
        this.addTableEntry(
                1, 14,
                new InfoEffect("you may cast the exiled card for as long as it remains exiled, " +
                        "and you may spend mana as though it were mana of any color to cast that spell")
        );
        this.addTableEntry(
                15, Integer.MAX_VALUE,
                new InfoEffect("you may cast the exiled card without paying " +
                        "its mana cost for as long as it remains exiled")
        );
    }

    private GalesRedirectionEffect(final GalesRedirectionEffect effect) {
        super(effect);
    }

    @Override
    public GalesRedirectionEffect copy() {
        return new GalesRedirectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (player == null || spell == null) {
            return false;
        }
        int manaValue = spell.getManaValue();
        player.moveCards(spell, Zone.EXILED, source, game);
        int result = player.rollDice(outcome, source, game, 20) + manaValue;
        Card card = spell.getMainCard();
        if (card == null || !Zone.EXILED.match(game.getState().getZone(card.getId()))) {
            return true;
        }
        if (result >= 15) {
            game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(
                    Zone.EXILED, TargetController.YOU, Duration.Custom, true
            ).setTargetPointer(new FixedTarget(card, game)), source);
        } else if (result >= 1) {
            CardUtil.makeCardPlayable(game, source, card, Duration.Custom, true);
        }
        return true;
    }
}

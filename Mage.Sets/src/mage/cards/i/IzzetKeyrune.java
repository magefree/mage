
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 * @author LevelX2
 */
public final class IzzetKeyrune extends CardImpl {

    public IzzetKeyrune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {T}: Add {U} or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // {U}{R}: Until end of turn, Izzet Keyrune becomes a 2/1 blue and red Elemental artifact creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new IzzetKeyruneToken(), "", Duration.EndOfTurn), new ManaCostsImpl("{U}{R}")));

        // Whenever Izzet Keyrune deals combat damage to a player, you may draw a card. If you do, discard a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new IzzetKeyruneEffect(), true));
    }

    public IzzetKeyrune(final IzzetKeyrune card) {
        super(card);
    }

    @Override
    public IzzetKeyrune copy() {
        return new IzzetKeyrune(this);
    }

    private static class IzzetKeyruneEffect extends OneShotEffect {

        public IzzetKeyruneEffect() {
            super(Outcome.DrawCard);
            this.staticText = "you may draw a card. If you do, discard a card";
        }

        public IzzetKeyruneEffect(final IzzetKeyruneEffect effect) {
            super(effect);
        }

        @Override
        public IzzetKeyruneEffect copy() {
            return new IzzetKeyruneEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null && player.chooseUse(Outcome.DrawCard, "Do you wish to draw a card? If you do, discard a card.", source, game)) {
                if (player.drawCards(1, game) > 0) {
                    player.discard(1, source, game);
                }
                return true;
            }
            return false;
        }
    }

    private static class IzzetKeyruneToken extends TokenImpl {
        IzzetKeyruneToken() {
            super("", "2/1 blue and red Elemental artifact creature");
            cardType.add(CardType.ARTIFACT);
            cardType.add(CardType.CREATURE);
            color.setBlue(true);
            color.setRed(true);
            this.subtype.add(SubType.ELEMENTAL);
            power = new MageInt(2);
            toughness = new MageInt(1);
        }

        public IzzetKeyruneToken(final IzzetKeyruneToken token) {
            super(token);
        }

        public IzzetKeyruneToken copy() {
            return new IzzetKeyruneToken(this);
        }
    }
}


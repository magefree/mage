package mage.cards.c;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author spjspj
 */
public final class CombustibleGearhulk extends CardImpl {

    public CombustibleGearhulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Combustible Gearhulk enters the battlefield, target opponent may have you draw three cards. If the player doesn't, put the top three cards of your library into your graveyard, then Combustible Gearhulk deals damage to that player equal to the total converted mana cost of those cards.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CombustibleGearhulkEffect(), false);

        ability.addTarget(new TargetOpponent(false));
        this.addAbility(ability);
    }

    public CombustibleGearhulk(final CombustibleGearhulk card) {
        super(card);
    }

    @Override
    public CombustibleGearhulk copy() {
        return new CombustibleGearhulk(this);
    }
}

class CombustibleGearhulkEffect extends OneShotEffect {

    public CombustibleGearhulkEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "target opponent may have you draw three cards. If the player doesn't, put the top three cards of your library into your graveyard, then {this} deals damage to that player equal to the total converted mana cost of those cards";
    }

    public CombustibleGearhulkEffect(final CombustibleGearhulkEffect effect) {
        super(effect);
    }

    @Override
    public CombustibleGearhulkEffect copy() {
        return new CombustibleGearhulkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Player opponent = game.getPlayer(source.getFirstTarget());
            if (opponent != null) {
                String questionDrawThree = "Have " + controller.getLogName() + " draw three cards?";
                if (opponent.chooseUse(outcome, questionDrawThree, source, game)) {
                    if (!game.isSimulation()) {
                        game.informPlayers(opponent.getLogName() + " lets " + controller.getLogName() + " draw three cards");
                    }
                    return new DrawCardSourceControllerEffect(3).apply(game, source);
                } else {
                    if (!game.isSimulation()) {
                        game.informPlayers(opponent.getLogName() + " does not let " + controller.getLogName() + " draw three cards");
                    }
                    return new CombustibleGearhulkMillAndDamageEffect().apply(game, source);
                }
            }
        }
        return false;
    }
}

class CombustibleGearhulkMillAndDamageEffect extends OneShotEffect {

    public CombustibleGearhulkMillAndDamageEffect() {
        super(Outcome.Damage);
        staticText = "put the top three cards of your library into your graveyard, then {this} deals damage to that player equal to the total converted mana cost of those cards.";
    }

    public CombustibleGearhulkMillAndDamageEffect(final CombustibleGearhulkMillAndDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int sumCMC = 0;
            Set<Card> cardList = controller.getLibrary().getTopCards(game, 3);
            for (Card card : cardList) {
                int test = card.getConvertedManaCost();
                sumCMC += test;
            }
            controller.moveCards(cardList, Zone.GRAVEYARD, source, game);
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(sumCMC, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public CombustibleGearhulkMillAndDamageEffect copy() {
        return new CombustibleGearhulkMillAndDamageEffect(this);
    }
}

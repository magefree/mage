
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class TrepanationBlade extends CardImpl {

    public TrepanationBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, defending player reveals cards from the top of their library until they reveal a land card.
        // The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into their graveyard.
        this.addAbility(new AttacksAttachedTriggeredAbility(new TrepanationBladeDiscardEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private TrepanationBlade(final TrepanationBlade card) {
        super(card);
    }

    @Override
    public TrepanationBlade copy() {
        return new TrepanationBlade(this);
    }
}

class TrepanationBladeDiscardEffect extends OneShotEffect {

    public TrepanationBladeDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player reveals cards from the top of their library until they reveal a land card. The creature gets +1/+0 until end of turn for each card revealed this way. That player puts the revealed cards into their graveyard";
    }

    public TrepanationBladeDiscardEffect(final TrepanationBladeDiscardEffect effect) {
        super(effect);
    }

    @Override
    public TrepanationBladeDiscardEffect copy() {
        return new TrepanationBladeDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature == null) {
                return false;
            }
            UUID defenderId = game.getCombat().getDefenderId(creature.getId());
            Player defendingPlayer = game.getPlayer(defenderId);
            if (defendingPlayer == null) {
                return false;
            }
            CardsImpl cards = new CardsImpl();
            for (Card card : defendingPlayer.getLibrary().getCards(game)) {
                cards.add(card);
                if (card.isLand(game)) {
                    break;
                }
            }
            defendingPlayer.revealCards(equipment.getName(), cards, game);
            defendingPlayer.moveCards(cards, Zone.GRAVEYARD, source, game);
            if (!cards.isEmpty()) {
                ContinuousEffect effect = new BoostTargetEffect(cards.size(), 0, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(creature, game));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

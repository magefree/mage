
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author jeffwadsworth
 */
public final class ScionOfDarkness extends CardImpl {

    public ScionOfDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}{B}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Scion of Darkness deals combat damage to a player, you may put target creature card from that player's graveyard onto the battlefield under your control.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new ScionOfDarknessEffect(), true, true);
        this.addAbility(ability);

        // Cycling {3}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{3}")));
    }

    public ScionOfDarkness(final ScionOfDarkness card) {
        super(card);
    }

    @Override
    public ScionOfDarkness copy() {
        return new ScionOfDarkness(this);
    }
}

class ScionOfDarknessEffect extends OneShotEffect {

    public ScionOfDarknessEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "you may put target creature card from that player's graveyard onto the battlefield under your control";
    }

    public ScionOfDarknessEffect(final ScionOfDarknessEffect effect) {
        super(effect);
    }

    @Override
    public ScionOfDarknessEffect copy() {
        return new ScionOfDarknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        FilterCard filter = new FilterCard("creature in " + damagedPlayer.getName() + "'s graveyard");
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new OwnerIdPredicate(damagedPlayer.getId()));
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
            if (controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        return true;
    }
}

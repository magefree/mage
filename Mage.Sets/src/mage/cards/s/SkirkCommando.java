
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BursegSardaukar
 */
public final class SkirkCommando extends CardImpl {

    public SkirkCommando(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        //Whenever Skirk Commando deals combat damage to a player, you may have it deal 2 damage to target creature that player controls.
        this.addAbility(new SkirkCommandoTriggeredAbility());

        //Morph {2}{R} (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        this.addAbility(new MorphAbility(new ManaCostsImpl("{2}{R}")));

    }

    private SkirkCommando(final SkirkCommando card) {
        super(card);
    }

    @Override
    public SkirkCommando copy() {
        return new SkirkCommando(this);
    }
}

class SkirkCommandoTriggeredAbility extends DealsCombatDamageToAPlayerTriggeredAbility {

    public SkirkCommandoTriggeredAbility() {
        super(new DamageTargetEffect(2), true, false);
    }

    public SkirkCommandoTriggeredAbility(SkirkCommandoTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null) {
                getTargets().clear();
                FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player " + player.getName() + " controls");
                filter.add(new ControllerIdPredicate(event.getPlayerId()));
                addTarget(new TargetCreaturePermanent(filter));
                return true;
            }
        }
        return false;
    }

    @Override
    public SkirkCommandoTriggeredAbility copy() {
        return new SkirkCommandoTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may have it deal 2 damage to target creature that player controls.";
    }
}


package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class YoreTillerNephilim extends CardImpl {

    public YoreTillerNephilim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}{R}");
        this.subtype.add(SubType.NEPHILIM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Yore-Tiller Nephilim attacks, return target creature card from your graveyard to the battlefield tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new YoreTillerNephilimEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard()));
        this.addAbility(ability);
    }

    public YoreTillerNephilim(final YoreTillerNephilim card) {
        super(card);
    }

    @Override
    public YoreTillerNephilim copy() {
        return new YoreTillerNephilim(this);
    }
}

class YoreTillerNephilimEffect extends OneShotEffect {

    public YoreTillerNephilimEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card from your graveyard to the battlefield tapped and attacking";
    }

    public YoreTillerNephilimEffect(final YoreTillerNephilimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    game.getCombat().addAttackingCreature(permanent.getId(), game);
                }
            }
            return true;

        }
        return false;
    }

    @Override
    public YoreTillerNephilimEffect copy() {
        return new YoreTillerNephilimEffect(this);
    }
}

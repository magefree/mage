
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class EaterOfTheDead extends CardImpl {

    public EaterOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {0}: If Eater of the Dead is tapped, exile target creature card from a graveyard and untap Eater of the Dead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new EaterOfTheDeadEffect(), new GenericManaCost(0));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard()));
        this.addAbility(ability);
    }

    public EaterOfTheDead(final EaterOfTheDead card) {
        super(card);
    }

    @Override
    public EaterOfTheDead copy() {
        return new EaterOfTheDead(this);
    }
}

class EaterOfTheDeadEffect extends OneShotEffect {
    EaterOfTheDeadEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "If {this} is tapped, exile target creature card from a graveyard and untap {this}";
    }

    EaterOfTheDeadEffect(final EaterOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Card card = game.getCard(source.getFirstTarget());
        if (sourcePermanent != null && sourcePermanent.isTapped() && card != null) {
            card.moveToExile(null, "Eater of the Dead", source.getSourceId(), game);
            sourcePermanent.untap(game);
        }
        return false;
    }

    @Override
    public EaterOfTheDeadEffect copy() {
        return new EaterOfTheDeadEffect(this);
    }

}

package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */

public final class CircleOfSolace extends CardImpl {

    public CircleOfSolace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W}");

        // As Circle of Solace enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Neutral)));
        // {1}{W}: The next time a creature of the chosen type would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CircleOfSolaceEffect(), new ManaCostsImpl<>("{1}{W}")));
    }

    private CircleOfSolace(final CircleOfSolace card) {
        super(card);
    }

    @Override
    public CircleOfSolace copy() {
        return new CircleOfSolace(this);
    }
}

class CircleOfSolaceEffect extends PreventionEffectImpl {

    public CircleOfSolaceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false);
        this.staticText = "The next time a creature of the chosen type would deal damage to you this turn, prevent that damage.";
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        this.used = true;
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!this.used && super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getAmount() > 0) {
                Permanent perm = game.getPermanent(event.getSourceId());
                if (perm != null) {
                    SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
                    return perm.getCardType().contains(CardType.CREATURE) && perm.getSubtype().contains(subType);
                }
            }
        }
        return false;
    }

    public CircleOfSolaceEffect(CircleOfSolaceEffect effect) {
        super(effect);
    }

    @Override
    public CircleOfSolaceEffect copy() {
        return new CircleOfSolaceEffect(this);
    }

}

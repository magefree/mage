
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Loki
 */
public final class InameLifeAspect extends CardImpl {

    private static final FilterCard filter = new FilterCard("Spirit cards from your graveyard");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public InameLifeAspect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Iname, Life Aspect dies, you may exile it. If you do, return any number of target Spirit cards from your graveyard to your hand.
        Ability ability = new DiesSourceTriggeredAbility(new InameLifeAspectEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(0, Integer.MAX_VALUE, filter));
        this.addAbility(ability);
    }

    private InameLifeAspect(final InameLifeAspect card) {
        super(card);
    }

    @Override
    public InameLifeAspect copy() {
        return new InameLifeAspect(this);
    }
}

class InameLifeAspectEffect extends OneShotEffect {

    public InameLifeAspectEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may exile it. If you do, return any number of target Spirit cards from your graveyard to your hand";
    }

    public InameLifeAspectEffect(final InameLifeAspectEffect effect) {
        super(effect);
    }

    @Override
    public InameLifeAspectEffect copy() {
        return new InameLifeAspectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (controller.chooseUse(outcome, "Exile " + sourceObject.getLogName() + " to return Spirit cards?", source, game)) {
                Effect effect = new ReturnToHandTargetEffect();
                effect.setTargetPointer(getTargetPointer());
                effect.getTargetPointer().init(game, source);
                new ExileSourceEffect().apply(game, source);
                return effect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}

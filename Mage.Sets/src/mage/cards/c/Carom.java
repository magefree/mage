package mage.cards.c;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Skyler Sell
 */
public final class Carom extends CardImpl {

    public Carom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // The next 1 damage that would be dealt to target creature this turn is dealt to another target creature instead.
        // Draw a card.
        this.getSpellAbility().addEffect(new CaromEffect(Duration.EndOfTurn, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to redirect from").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("to redirect to").setTargetTag(2));

        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private Carom(final Carom card) {
        super(card);
    }

    @Override
    public Carom copy() {
        return new Carom(this);
    }
}

class CaromEffect extends RedirectionEffect {

    protected MageObjectReference redirectToObject;

    public CaromEffect(Duration duration, int amount) {
        super(duration, amount, UsageType.ONE_USAGE_ABSOLUTE);
        staticText = "The next " + amount + " damage that would be dealt to target creature this turn is dealt to another target creature instead";
    }

    private CaromEffect(final CaromEffect effect) {
        super(effect);
    }

    @Override
    public CaromEffect copy() {
        return new CaromEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        redirectToObject = new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            if (game.getControllerId(redirectToObject.getSourceId()) != null) {
                if (redirectToObject.equals(new MageObjectReference(source.getTargets().get(1).getFirstTarget(), game))) {
                    redirectTarget = source.getTargets().get(1);
                    return true;
                }
            }
        }
        return false;
    }
}

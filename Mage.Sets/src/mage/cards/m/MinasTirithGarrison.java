package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MinasTirithGarrison extends CardImpl {

    public MinasTirithGarrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Minas Tirith Garrison's power is equal to the number of cards in your hand.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerSourceEffect(CardsInControllerHandCount.instance)
        ));

        // Whenever Minas Tirith Garrison attacks, you may tap any number of untapped Humans you control. Draw a card for each Human tapped this way.
        this.addAbility(new AttacksTriggeredAbility(
                new MinasTirithGarrisonEffect(), true
        ));
    }

    private MinasTirithGarrison(final MinasTirithGarrison card) {
        super(card);
    }

    @Override
    public MinasTirithGarrison copy() {
        return new MinasTirithGarrison(this);
    }
}

// Based of Devout Invocation.
class MinasTirithGarrisonEffect extends OneShotEffect {

    static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.HUMAN, "untapped Humans you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public MinasTirithGarrisonEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "tap any number of untapped Humans you control. Draw a card for each Human tapped this way";
    }

    private MinasTirithGarrisonEffect(final MinasTirithGarrisonEffect effect) {
        super(effect);
    }

    @Override
    public MinasTirithGarrisonEffect copy() {
        return new MinasTirithGarrisonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TargetPermanent target = new TargetControlledPermanent(0, Integer.MAX_VALUE, filter, true);
        controller.choose(outcome, target, source, game);
        if (target.getTargets().isEmpty()) {
            return false;
        }

        int tappedAmount = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.tap(source, game)) {
                tappedAmount++;
            }
        }

        return new DrawCardSourceControllerEffect(tappedAmount).apply(game, source);
    }
}

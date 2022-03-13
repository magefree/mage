
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DiscipleOfBolas extends CardImpl {

    public DiscipleOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Disciple of Bolas enters the battlefield, sacrifice another creature. You gain X life and draw X cards, where X is that creature's power.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscipleOfBolasEffect()));
    }

    private DiscipleOfBolas(final DiscipleOfBolas card) {
        super(card);
    }

    @Override
    public DiscipleOfBolas copy() {
        return new DiscipleOfBolas(this);
    }
}

class DiscipleOfBolasEffect extends OneShotEffect {

    public DiscipleOfBolasEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice another creature. You gain X life and draw X cards, where X is that creature's power";
    }

    public DiscipleOfBolasEffect(final DiscipleOfBolasEffect effect) {
        super(effect);
    }

    @Override
    public DiscipleOfBolasEffect copy() {
        return new DiscipleOfBolasEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true);
            target.setRequired(true);
            if (target.canChoose(source.getControllerId(), source, game)) {
                controller.chooseTarget(outcome, target, source, game);
                Permanent sacrificed = game.getPermanent(target.getFirstTarget());
                if (sacrificed != null) {
                    sacrificed.sacrifice(source, game);
                    int power = sacrificed.getPower().getValue();
                    controller.gainLife(power, game, source);
                    controller.drawCards(power, source, game);
                }
            }
            return true;
        }
        return false;
    }
}

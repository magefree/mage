
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class ImpetuousProtege extends CardImpl {

    public ImpetuousProtege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Partner with Proud Mentor (When this creature enters the battlefield, target player may put Proud Mentor into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Proud Mentor"));

        // Whenever Impetuous Protege attacks, it gets +X/+0 until end of turn, where X is the greatest power among tapped creatures your opponents control
        this.addAbility(new AttacksTriggeredAbility(new ImpetuousProtegeEffect(), false));
    }

    private ImpetuousProtege(final ImpetuousProtege card) {
        super(card);
    }

    @Override
    public ImpetuousProtege copy() {
        return new ImpetuousProtege(this);
    }
}

class ImpetuousProtegeEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    ImpetuousProtegeEffect() {
        super(Outcome.Benefit);
        this.staticText = "it gets +X/+0 until end of turn, where X is the greatest power among tapped creatures your opponents control";
    }

    ImpetuousProtegeEffect(final ImpetuousProtegeEffect effect) {
        super(effect);
    }

    @Override
    public ImpetuousProtegeEffect copy() {
        return new ImpetuousProtegeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int maxPower = 0;
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            maxPower = Math.max(maxPower, creature.getPower().getValue());
        }
        game.addEffect(new BoostSourceEffect(maxPower, 0, Duration.EndOfTurn), source);
        return true;
    }
}

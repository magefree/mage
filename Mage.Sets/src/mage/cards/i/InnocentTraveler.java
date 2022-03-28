package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InnocentTraveler extends CardImpl {

    public InnocentTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.m.MaliciousInvader.class;

        // At the beginning of your upkeep, any opponent may sacrifice a creature. If no one does, transform Innocent Traveler.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new InnocentTravelerEffect(), TargetController.YOU, false
        ));
    }

    private InnocentTraveler(final InnocentTraveler card) {
        super(card);
    }

    @Override
    public InnocentTraveler copy() {
        return new InnocentTraveler(this);
    }
}

class InnocentTravelerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creature to sacrifice");

    InnocentTravelerEffect() {
        super(Outcome.Benefit);
        staticText = "any opponent may sacrifice a creature. If no one does, transform {this}";
    }

    private InnocentTravelerEffect(final InnocentTravelerEffect effect) {
        super(effect);
    }

    @Override
    public InnocentTravelerEffect copy() {
        return new InnocentTravelerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = false;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (!target.canChoose(opponent.getId(), source, game)
                    || !opponent.chooseUse(Outcome.AIDontUseIt, "Sacrifice a creature?", source, game)
                    || !opponent.choose(Outcome.Sacrifice, target, source, game)) {
                continue;
            }
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent == null || !permanent.sacrifice(source, game)) {
                continue;
            }
            flag = true;
        }
        if (flag) {
            return true;
        }
        Permanent sourcePerm = source.getSourcePermanentIfItStillExists(game);
        if (sourcePerm != null) {
            sourcePerm.transform(source, game);
        }
        return true;
    }
}

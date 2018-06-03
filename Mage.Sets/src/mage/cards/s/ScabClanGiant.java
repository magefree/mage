
package mage.cards.s;

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
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ScabClanGiant extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public ScabClanGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Scab-Clan Giant enters the battlefield, it fights target creature an opponent controls chosen at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ScabClanGiantEffect());
        Target target = new TargetCreaturePermanent(filter);
        target.setRandom(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public ScabClanGiant(final ScabClanGiant card) {
        super(card);
    }

    @Override
    public ScabClanGiant copy() {
        return new ScabClanGiant(this);
    }
}

class ScabClanGiantEffect extends OneShotEffect {

    public ScabClanGiantEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} fights target creature an opponent controls chosen at random";
    }

    public ScabClanGiantEffect(final ScabClanGiantEffect effect) {
        super(effect);
    }

    @Override
    public ScabClanGiantEffect copy() {
        return new ScabClanGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature1 = game.getPermanent(source.getSourceId());
        Permanent creature2 = game.getPermanent(source.getFirstTarget());
        // 20110930 - 701.10
        if (creature1 != null && creature2 != null) {
            if (creature1.isCreature() && creature2.isCreature()) {
                return creature1.fight(creature2, source, game);
            }
        }
        return false;
    }
}
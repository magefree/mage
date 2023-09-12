
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class CapriciousEfreet extends CardImpl {

    private static final FilterNonlandPermanent filterControlled = new FilterNonlandPermanent("nonland permanent you control");
    private static final FilterNonlandPermanent filterNotControlled = new FilterNonlandPermanent("nonland permanent you don't control");

    static {
        filterControlled.add(TargetController.YOU.getControllerPredicate());
        filterNotControlled.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public CapriciousEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.EFREET);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, choose target nonland permanent you control and up to two target nonland permanents you don't control. Destroy one of them at random.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CapriciousEfreetEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetPermanent(filterControlled));
        ability.addTarget(new TargetPermanent(0, 2, filterNotControlled, false));
        this.addAbility(ability);
    }

    private CapriciousEfreet(final CapriciousEfreet card) {
        super(card);
    }

    @Override
    public CapriciousEfreet copy() {
        return new CapriciousEfreet(this);
    }
}

class CapriciousEfreetEffect extends OneShotEffect {

    public CapriciousEfreetEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "choose target nonland permanent you control and up to two target nonland permanents you don't control. Destroy one of them at random";
    }

    private CapriciousEfreetEffect(final CapriciousEfreetEffect effect) {
        super(effect);
    }

    @Override
    public CapriciousEfreetEffect copy() {
        return new CapriciousEfreetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> targetPermanents = new ArrayList<>();
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            targetPermanents.add(permanent);
        }
        for (UUID targetID : source.getTargets().get(1).getTargets()) {
            permanent = game.getPermanent(targetID);
            if (permanent != null) {
                targetPermanents.add(permanent);
            }
        }

        if (!targetPermanents.isEmpty()) {
            permanent = targetPermanents.get(RandomUtil.nextInt(targetPermanents.size()));
            permanent.destroy(source, game, false);
            return true;
        }
        return false;
    }
}

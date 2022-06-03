
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author cbt33
 */
public final class NantukoMentor extends CardImpl {

    public NantukoMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{G}, {tap}: Target creature gets +X/+X until end of turn, where X is that creature's power.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NantukoMentorEffect(), new ManaCostsImpl<>("{2}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NantukoMentor(final NantukoMentor card) {
        super(card);
    }

    @Override
    public NantukoMentor copy() {
        return new NantukoMentor(this);
    }
}

class NantukoMentorEffect extends OneShotEffect {

    public NantukoMentorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target creature gets +X/+X until end of turn, where X is that creature's power";
    }

    public NantukoMentorEffect(final NantukoMentorEffect effect) {
        super(effect);
    }

    @Override
    public NantukoMentorEffect copy() {
        return new NantukoMentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetPermanent != null) {
            ContinuousEffect effect = new BoostTargetEffect(targetPermanent.getPower().getValue(), targetPermanent.getPower().getValue(), Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(targetPermanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NyssaOfTraken extends CardImpl {

    public NyssaOfTraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You have no maximum hand size.
        this.addAbility(new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        )));

        // Sonic Booster -- Whenever Nyssa of Traken attacks, sacrifice any number of artifacts. When you sacrifice one or more artifacts this way, tap up to that many target creatures and draw that many cards.
        this.addAbility(new AttacksTriggeredAbility(new NyssaOfTrakenEffect()).withFlavorWord("Sonic Booster"));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private NyssaOfTraken(final NyssaOfTraken card) {
        super(card);
    }

    @Override
    public NyssaOfTraken copy() {
        return new NyssaOfTraken(this);
    }
}

class NyssaOfTrakenEffect extends OneShotEffect {

    NyssaOfTrakenEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice any number of artifacts. When you sacrifice one or more artifacts this way, " +
                "tap up to that many target creatures and draw that many cards.";
    }

    private NyssaOfTrakenEffect(final NyssaOfTrakenEffect effect) {
        super(effect);
    }

    @Override
    public NyssaOfTrakenEffect copy() {
        return new NyssaOfTrakenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_ARTIFACTS
        );
        player.choose(outcome, target, source, game);
        int count = 0;
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                count++;
            }
        }
        if (count < 1) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DrawCardSourceControllerEffect(count).concatBy("and"));
        ability.addTarget(new TargetCreaturePermanent(0, count));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

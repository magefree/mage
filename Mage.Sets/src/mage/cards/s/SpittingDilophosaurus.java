package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class SpittingDilophosaurus extends CardImpl {

    public SpittingDilophosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Spitting Dilophosaurus enters the battlefield or attacks, put a -1/-1 counter on up to one target creature.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
        // Creatures your opponents control with -1/-1 counters on them can't block.
        this.addAbility(new SimpleStaticAbility(new SpittingDilophosaurusRestrictionEffect()));
    }

    private SpittingDilophosaurus(final SpittingDilophosaurus card) {
        super(card);
    }

    @Override
    public SpittingDilophosaurus copy() {
        return new SpittingDilophosaurus(this);
    }
}
//Based on Kulrath Knight/UnleashAbility
class SpittingDilophosaurusRestrictionEffect extends RestrictionEffect {

    public SpittingDilophosaurusRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures your opponents control with -1/-1 counters on them can't block.";
    }

    private SpittingDilophosaurusRestrictionEffect(final SpittingDilophosaurusRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public SpittingDilophosaurusRestrictionEffect copy() {
        return new SpittingDilophosaurusRestrictionEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && permanent != null && game.isOpponent(player, permanent.getControllerId())) {
            return permanent.getCounters(game).getCount(CounterType.M1M1) > 0;
        }
        return false;
    }
}

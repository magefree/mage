package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SwordOfTruthAndJustice extends CardImpl {

    public SwordOfTruthAndJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from white and from blue.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLUE), AttachmentType.EQUIPMENT
        ).setText("and has protection from white and from blue"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, put a +1/+1 counter on a creature you control, then proliferate.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SwordOfTruthAndJusticeEffect(), "equipped creature", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), new TargetControlledCreaturePermanent(), false));
    }

    private SwordOfTruthAndJustice(final SwordOfTruthAndJustice card) {
        super(card);
    }

    @Override
    public SwordOfTruthAndJustice copy() {
        return new SwordOfTruthAndJustice(this);
    }
}

class SwordOfTruthAndJusticeEffect extends OneShotEffect {

    SwordOfTruthAndJusticeEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on a creature you control, then proliferate. " +
                "<i>(Choose any number of permanents and/or players, then give each another counter of each kind already there.)</i>";
    }

    private SwordOfTruthAndJusticeEffect(final SwordOfTruthAndJusticeEffect effect) {
        super(effect);
    }

    @Override
    public SwordOfTruthAndJusticeEffect copy() {
        return new SwordOfTruthAndJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (player.choose(outcome, target, source, game)) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
        }
        return new ProliferateEffect(true).apply(game, source);
    }
}
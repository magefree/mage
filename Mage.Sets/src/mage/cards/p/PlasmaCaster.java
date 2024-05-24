package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.BlockingOrBlockedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PlasmaCaster extends CardImpl {

    private static final FilterCreaturePermanent filter =
            new FilterCreaturePermanent("creature that's blocking equipped creature");

    static {
        filter.add(PlasmaCasterPredicate.instance);
    }

    public PlasmaCaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature attacks, you get {E}{E}.
        this.addAbility(new AttacksAttachedTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));

        // Pay {E}{E}: Choose target creature that's blocking equipped creature. Flip a coin. If you win the flip, exile the chosen creature. Otherwise, Plasma Caster deals 1 damage to it.
        Ability ability = new SimpleActivatedAbility(
                new FlipCoinEffect(
                        new ExileTargetEffect(),
                        new DamageTargetEffect(1)
                ).setText("Choose target creature that's blocking equipped creature. "
                        + "Flip a coin. If you win the flip, exile the chosen creature. "
                        + "Otherwise, {this} deals 1 damage to it"),
                new PayEnergyCost(2)
        );
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private PlasmaCaster(final PlasmaCaster card) {
        super(card);
    }

    @Override
    public PlasmaCaster copy() {
        return new PlasmaCaster(this);
    }
}

enum PlasmaCasterPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Ability source = input.getSource();
        if (source == null) {
            return false;
        }
        Permanent equipment = (Permanent) source.getSourceObject(game);
        if (equipment == null) {
            return false;
        }
        Permanent creature = game.getPermanent(equipment.getAttachedTo());
        if (creature == null) {
            return false;
        }
        return BlockingOrBlockedWatcher.check(creature, input.getObject(), game);
    }
}

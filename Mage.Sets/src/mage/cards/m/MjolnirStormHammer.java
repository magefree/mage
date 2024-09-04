package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsAttachedAttackingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Grath
 */
public final class MjolnirStormHammer extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("tapped creatures that opponent controls");

    static {
        filter.add(DefendingPlayerControlsAttachedAttackingPredicate.instance);
        filter2.add(DefendingPlayerControlsAttachedAttackingPredicate.instance);
        filter2.add(TappedPredicate.TAPPED);
    }

    public MjolnirStormHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // When Mjolnir enters the battlefield, attach it to target legendary creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY));

        // Whenever equipped creature attacks, tap target creature defending player controls and put a stun counter on it. Then Mjolnir deals damage to each opponent equal to the number of tapped creatures that opponent controls.
        Ability ability = new AttacksAttachedTriggeredAbility(
                new TapTargetEffect(), AttachmentType.EQUIPMENT, false
        );
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .setText(" and put a stun counter on it"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new DamagePlayersEffect(TappedCreaturesControlledByTargetsOwnerCount.instance, TargetController.OPPONENT));
        this.addAbility(ability);

        // Equip {4}
        this.addAbility(new EquipAbility(4, false));
    }

    private MjolnirStormHammer(final MjolnirStormHammer card) {
        super(card);
    }

    @Override
    public MjolnirStormHammer copy() {
        return new MjolnirStormHammer(this);
    }
}

enum TappedCreaturesControlledByTargetsOwnerCount implements DynamicValue {
    instance;

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = game.getPermanent(sourceAbility.getFirstTarget());
        if (permanent != null) {
            return game.getBattlefield().countAll(filter, permanent.getControllerId(), game);
        }
        return 0;
    }

    @Override
    public TappedCreaturesControlledByTargetsOwnerCount copy() {
        return this;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "tapped creatures that opponent controls";
    }
}
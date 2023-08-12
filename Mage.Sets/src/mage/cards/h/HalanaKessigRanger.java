package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HalanaKessigRanger extends CardImpl {

    public HalanaKessigRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever another creature enters the battlefield under your control, you may pay {2}. When you do, that creature deals damage equal to its power to target creature.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new DoIfCostPaid(new HalanaKessigRangerTriggerEffect(), new GenericManaCost(2)),
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, false, SetTargetPointer.PERMANENT,
                "Whenever another creature enters the battlefield under your control, you may pay {2}. " +
                        "When you do, that creature deals damage equal to its power to target creature."
        ));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private HalanaKessigRanger(final HalanaKessigRanger card) {
        super(card);
    }

    @Override
    public HalanaKessigRanger copy() {
        return new HalanaKessigRanger(this);
    }
}

class HalanaKessigRangerTriggerEffect extends OneShotEffect {

    HalanaKessigRangerTriggerEffect() {
        super(Outcome.Benefit);
        staticText = "that creature deals damage equal to its power to target creature";
    }

    private HalanaKessigRangerTriggerEffect(final HalanaKessigRangerTriggerEffect effect) {
        super(effect);
    }

    @Override
    public HalanaKessigRangerTriggerEffect copy() {
        return new HalanaKessigRangerTriggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new HalanaKessigRangerDamageEffect(
                        new MageObjectReference(targetPointer.getFirst(game, source), game)
                ), false, "that creature deals damage equal to its power to target creature"
        );
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class HalanaKessigRangerDamageEffect extends OneShotEffect {

    private final MageObjectReference mor;

    HalanaKessigRangerDamageEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private HalanaKessigRangerDamageEffect(final HalanaKessigRangerDamageEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public HalanaKessigRangerDamageEffect copy() {
        return new HalanaKessigRangerDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent1 = mor.getPermanentOrLKIBattlefield(game);
        Permanent permanent2 = game.getPermanent(source.getFirstTarget());
        if (permanent1 == null || !permanent1.isCreature(game) || permanent2 == null) {
            return false;
        }
        permanent2.damage(permanent1.getPower().getValue(), permanent1.getId(), source, game);
        return true;
    }
}

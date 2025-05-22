package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.EachTargetPointer;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class WeaponsVendor extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledPermanent(SubType.EQUIPMENT, "you control an Equipment")
    );
    private static final Hint hint = new ConditionHint(condition);

    public WeaponsVendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // At the beginning of combat on your turn, if you control an Equipment, you may pay {1}. When you do, attach target Equipment you control to target creature you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new WeaponsVendorEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoWhenCostPaid(
                ability, new GenericManaCost(1),
                "Pay {1} to attach an equipment to a creature you control?"
        )).withInterveningIf(condition).addHint(hint));
    }

    private WeaponsVendor(final WeaponsVendor card) {
        super(card);
    }

    @Override
    public WeaponsVendor copy() {
        return new WeaponsVendor(this);
    }
}

class WeaponsVendorEffect extends OneShotEffect {

    WeaponsVendorEffect() {
        super(Outcome.Benefit);
        staticText = "attach target Equipment you control to target creature you control";
        this.setTargetPointer(new EachTargetPointer());
    }

    private WeaponsVendorEffect(final WeaponsVendorEffect effect) {
        super(effect);
    }

    @Override
    public WeaponsVendorEffect copy() {
        return new WeaponsVendorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this.getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return permanents.size() >= 2 && permanents.get(1).addAttachment(permanents.get(0).getId(), source, game);
    }
}

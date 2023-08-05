package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.DealCombatDamageControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.TargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TadeasJuniperAscendant extends CardImpl {

    private static final Condition condition = new InvertCondition(SourceAttackingCondition.instance);
    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("a creature you control with reach");

    static {
        filter.add(new AbilityPredicate(ReachAbility.class));
    }

    public TadeasJuniperAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Tadeas has hexproof unless it's attacking.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                condition, "{this} has hexproof unless he's attacking"
        )));

        // Whenever a creature you control with reach attacks, untap it and it can't be blocked by creatures with greater power this combat.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(
                new TadeasJuniperAscendantEffect(), false, filter, true
        ));

        // Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new DealCombatDamageControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ));
    }

    private TadeasJuniperAscendant(final TadeasJuniperAscendant card) {
        super(card);
    }

    @Override
    public TadeasJuniperAscendant copy() {
        return new TadeasJuniperAscendant(this);
    }
}

class TadeasJuniperAscendantEffect extends OneShotEffect {

    TadeasJuniperAscendantEffect() {
        super(Outcome.Benefit);
        staticText = "untap it and it can't be blocked by creatures with greater power this combat";
    }

    private TadeasJuniperAscendantEffect(final TadeasJuniperAscendantEffect effect) {
        super(effect);
    }

    @Override
    public TadeasJuniperAscendantEffect copy() {
        return new TadeasJuniperAscendantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.untap(game);
        game.addEffect(new TadeasJuniperAscendantEvasionEffect(getTargetPointer()), source);
        return true;
    }
}

class TadeasJuniperAscendantEvasionEffect extends RestrictionEffect {

    TadeasJuniperAscendantEvasionEffect(TargetPointer targetPointer) {
        super(Duration.EndOfCombat);
        this.targetPointer = targetPointer;
        staticText = "and can't be blocked by creatures with greater power";
    }

    private TadeasJuniperAscendantEvasionEffect(final TadeasJuniperAscendantEvasionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return this.targetPointer.getTargets(game, source).contains(permanent.getId());
    }

    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() <= attacker.getPower().getValue();
    }

    @Override
    public TadeasJuniperAscendantEvasionEffect copy() {
        return new TadeasJuniperAscendantEvasionEffect(this);
    }
}
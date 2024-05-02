package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.ControlACommanderHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetAnyTarget;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SiegeGangLieutenant extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.GOBLIN, "a Goblin");

    public SiegeGangLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lieutenant -- At the beginning of combat on your turn, if you control your commander, create two 1/1 red Goblin creature tokens. Those tokens gain haste until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new SiegeGangLieutenantEffect(), TargetController.YOU, false
                ), CommanderInPlayCondition.instance, "At the beginning of combat on your turn, " +
                "if you control your commander, create two 1/1 red Goblin creature tokens. " +
                "Those tokens gain haste until end of turn."
        ).setAbilityWord(AbilityWord.LIEUTENANT));

        // {2}, Sacrifice a Goblin: Siege-Gang Lieutenant deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SiegeGangLieutenant(final SiegeGangLieutenant card) {
        super(card);
    }

    @Override
    public SiegeGangLieutenant copy() {
        return new SiegeGangLieutenant(this);
    }
}

class SiegeGangLieutenantEffect extends OneShotEffect {

    SiegeGangLieutenantEffect() {
        super(Outcome.Benefit);
    }

    private SiegeGangLieutenantEffect(final SiegeGangLieutenantEffect effect) {
        super(effect);
    }

    @Override
    public SiegeGangLieutenantEffect copy() {
        return new SiegeGangLieutenantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new GoblinToken();
        token.putOntoBattlefield(2, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}

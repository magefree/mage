package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaffineSchemingSeer extends CardImpl {

    private static final Hint hint = new ValueHint(
            "Attacking creatures", new AttackingCreatureCount("attacking creatures")
    );

    public RaffineSchemingSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}")));

        // Whenever you attack, target creature connives X, where X is the number of attacking creatures.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new RaffineSchemingSeerEffect(), 1);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability.addHint(hint));
    }

    private RaffineSchemingSeer(final RaffineSchemingSeer card) {
        super(card);
    }

    @Override
    public RaffineSchemingSeer copy() {
        return new RaffineSchemingSeer(this);
    }
}

class RaffineSchemingSeerEffect extends OneShotEffect {

    RaffineSchemingSeerEffect() {
        super(Outcome.Benefit);
        staticText = "target creature connives X, where X is the number of attacking creatures. " +
                "<i>(Draw X cards, then discard X cards. Put a +1/+1 counter on that creature " +
                "for each nonland card discarded this way.)</i>";
    }

    private RaffineSchemingSeerEffect(final RaffineSchemingSeerEffect effect) {
        super(effect);
    }

    @Override
    public RaffineSchemingSeerEffect copy() {
        return new RaffineSchemingSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int amount = game.getBattlefield().count(
                StaticFilters.FILTER_ATTACKING_CREATURES,
                source.getControllerId(), source, game
        );
        return ConniveSourceEffect.connive(permanent, amount, source, game);
    }
}

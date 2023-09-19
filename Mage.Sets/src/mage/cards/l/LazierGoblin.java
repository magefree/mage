package mage.cards.l;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.ConditionPermanentHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LazierGoblin extends CardImpl {

    /**
     * Key to store that a MOR permanent has been motivated by a player.
     */
    static final String keyMotivation(UUID playerId, MageObjectReference mor) {
        return "LazierGoblinMotivated|" + playerId.toString() + "|" + mor.getSourceId().toString() + "|" + mor.getZoneChangeCounter();
    }

    public LazierGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Motivate {3}{R} (This creature can't attack or block unless you have paid its motivate cost once. Motivate only as a sorcery.)
        this.addAbility(new LazierGoblinSpecialAction());
        this.addAbility(new SimpleStaticAbility(new LazierGoblinRestrictionEffect())
                .addHint(new ConditionPermanentHint(LazierGoblinMotivatedCondition.instance)));

        // When Lazier Goblin enters the battlefield, it deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LazierGoblin(final LazierGoblin card) {
        super(card);
    }

    @Override
    public LazierGoblin copy() {
        return new LazierGoblin(this);
    }
}

class LazierGoblinSpecialAction extends SpecialAction {

    // Ruling (2019-11-12):
    // Paying a creature's motivate cost is a special action that its controller
    // may take any time they have priority during their main phase with no spells
    // or abilities on the stack.

    LazierGoblinSpecialAction() {
        super(Zone.BATTLEFIELD);
        this.setTiming(TimingRule.SORCERY);
        this.addCost(new ManaCostsImpl<>("{3}{R}"));
        this.addEffect(new LazierGoblinMotivateEffect().setText("Motivate {3}{R}")); // text is for game log to make sense.
    }

    private LazierGoblinSpecialAction(final LazierGoblinSpecialAction ability) {
        super(ability);
    }

    @Override
    public LazierGoblinSpecialAction copy() {
        return new LazierGoblinSpecialAction(this);
    }

    @Override
    public String getRule() {
        return "Motivate {3}{R} <i>(This creature can't attack or block unless you have paid its motivate cost once. Motivate only as a sorcery.)</i>";
    }
}

class LazierGoblinMotivateEffect extends OneShotEffect {
    LazierGoblinMotivateEffect() {
        super(Outcome.Benefit);
    }

    private LazierGoblinMotivateEffect(final LazierGoblinMotivateEffect effect) {
        super(effect);
    }

    @Override
    public LazierGoblinMotivateEffect copy() {
        return new LazierGoblinMotivateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        if (permanent == null) {
            return false;
        }
        game.getState().setValue(
                LazierGoblin.keyMotivation(
                        permanent.getControllerId(),
                        new MageObjectReference(permanent, game)
                ),
                true
        );
        return true;
    }
}

class LazierGoblinRestrictionEffect extends RestrictionEffect {

    public LazierGoblinRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = ""; // no text.
    }

    private LazierGoblinRestrictionEffect(final LazierGoblinRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public LazierGoblinRestrictionEffect copy() {
        return new LazierGoblinRestrictionEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent != null
                && permanent.getId().equals(source.getSourceId())
                && null == game.getState().getValue(
                        LazierGoblin.keyMotivation(
                                permanent.getControllerId(),
                                new MageObjectReference(permanent, game)
                        )
                );
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }
}

enum LazierGoblinMotivatedCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        return permanent != null
                && null != game.getState().getValue(
                        LazierGoblin.keyMotivation(
                                permanent.getControllerId(),
                                new MageObjectReference(permanent, game)
                        )
                );
    }

    @Override
    public String toString() {
        return "motivated";
    }
}
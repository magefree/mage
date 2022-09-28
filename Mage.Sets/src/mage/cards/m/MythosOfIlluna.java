package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MythosOfIlluna extends CardImpl {

    public MythosOfIlluna(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // Create a token that's a copy of target permanent. If {R}{G} was spent to cast this spell, instead create a token that's a copy of that permanent, except the token has "When this permanent enters the battlefield, if it's a creature, it fights up to one target creature you don't control."
        this.getSpellAbility().addEffect(new MythosOfIllunaEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private MythosOfIlluna(final MythosOfIlluna card) {
        super(card);
    }

    @Override
    public MythosOfIlluna copy() {
        return new MythosOfIlluna(this);
    }
}

class MythosOfIllunaEffect extends OneShotEffect {

    private static final Condition condition = new CompoundCondition(
            new ManaWasSpentCondition(ColoredManaSymbol.R),
            new ManaWasSpentCondition(ColoredManaSymbol.G)
    );

    MythosOfIllunaEffect() {
        super(Outcome.Benefit);
        staticText = "Create a token that's a copy of target permanent. " +
                "If {R}{G} was spent to cast this spell, instead create a token that's a copy of that permanent, " +
                "except the token has \"When this permanent enters the battlefield, if it's a creature, " +
                "it fights up to one target creature you don't control.\"";
    }

    private MythosOfIllunaEffect(final MythosOfIllunaEffect effect) {
        super(effect);
    }

    @Override
    public MythosOfIllunaEffect copy() {
        return new MythosOfIllunaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId());
        if (condition.apply(game, source)) {
            Ability ability = new ConditionalInterveningIfTriggeredAbility(
                    new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()),
                    MythosOfIllunaCondition.instance, "When this permanent enters the battlefield, " +
                    "if it's a creature, it fights up to one target creature you don't control."
            );
            ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
            effect.addAdditionalAbilities(ability);
        }
        return effect.apply(game, source);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}

enum MythosOfIllunaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        return permanent != null && permanent.isCreature(game);
    }
}

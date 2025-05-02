package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.InstantAndSorceryCastThisTurn;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RionyaFireDancer extends CardImpl {

    public RionyaFireDancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, create X tokens that are copies of another target creature you control, where X is one plus the number of instant and sorcery spells you've cast this turn. They gain haste. Exile them at the beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new RionyaFireDancerEffect()
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
        this.addAbility(ability.addHint(InstantAndSorceryCastThisTurn.YOU.getHint()));
    }

    private RionyaFireDancer(final RionyaFireDancer card) {
        super(card);
    }

    @Override
    public RionyaFireDancer copy() {
        return new RionyaFireDancer(this);
    }
}

class RionyaFireDancerEffect extends OneShotEffect {

    RionyaFireDancerEffect() {
        super(Outcome.Benefit);
        staticText = "create X tokens that are copies of another target creature you control, " +
                "where X is one plus the number of instant and sorcery spells you've cast this turn. " +
                "They gain haste. Exile them at the beginning of the next end step";
    }

    private RionyaFireDancerEffect(final RionyaFireDancerEffect effect) {
        super(effect);
    }

    @Override
    public RionyaFireDancerEffect copy() {
        return new RionyaFireDancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, true,
                InstantAndSorceryCastThisTurn.YOU.calculate(game, source, this) + 1
        );
        effect.apply(game, source);
        effect.exileTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
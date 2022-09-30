package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MishraEminentOne extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledArtifactPermanent("noncreature artifact you control");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public MishraEminentOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, create a token that's a copy of target noncreature artifact you control, except its name is Mishra's Warform and it's a 4/4 Construct artifact creature in addition to its other types. It gains haste until end of turn. Sacrifice it at the beginning of the next end step.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new MishraEminentOneEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MishraEminentOne(final MishraEminentOne card) {
        super(card);
    }

    @Override
    public MishraEminentOne copy() {
        return new MishraEminentOne(this);
    }
}

class MishraEminentOneEffect extends OneShotEffect {

    MishraEminentOneEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of target noncreature artifact you control, " +
                "except its name is Mishra's Warform and it's a 4/4 Construct artifact creature " +
                "in addition to its other types. It gains haste until end of turn. " +
                "Sacrifice it at the beginning of the next end step";
    }

    private MishraEminentOneEffect(final MishraEminentOneEffect effect) {
        super(effect);
    }

    @Override
    public MishraEminentOneEffect copy() {
        return new MishraEminentOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect().setPermanentModifier((token, g) -> {
            token.setName("Mishra's Warform");
            token.setPower(4);
            token.setToughness(4);
            token.addCardType(CardType.ARTIFACT, CardType.CREATURE);
            token.addSubType(SubType.CONSTRUCT);
        });
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}

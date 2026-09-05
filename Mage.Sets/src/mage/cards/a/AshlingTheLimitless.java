package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainEvokeAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterPermanentCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author nandmp
 */
public final class AshlingTheLimitless extends CardImpl {

    private static final FilterControlledPermanent sacrificeFilter
            = new FilterControlledPermanent("nontoken Elemental you control");
    private static final FilterPermanentCard evokeFilter
            = new FilterPermanentCard("Elemental permanent card");

    static {
        sacrificeFilter.add(SubType.ELEMENTAL.getPredicate());
        sacrificeFilter.add(TokenPredicate.FALSE);
        evokeFilter.add(SubType.ELEMENTAL.getPredicate());
    }

    public AshlingTheLimitless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Elemental permanent spells you cast from your hand gain evoke {4} as you cast them.
        this.addAbility(new SimpleStaticAbility(new GainEvokeAbilityEffect(
                new ManaCostsImpl<>("{4}"), evokeFilter, Zone.HAND,
                "Elemental permanent spells you cast from your hand gain evoke {4} as you cast them"
        )));

        // Whenever you sacrifice a nontoken Elemental, create a token that's a copy of it. The token gains haste until end of turn. At the beginning of your next end step, sacrifice it unless you pay {W}{U}{B}{R}{G}.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AshlingTheLimitlessEffect(), sacrificeFilter
        ));
    }

    private AshlingTheLimitless(final AshlingTheLimitless card) {
        super(card);
    }

    @Override
    public AshlingTheLimitless copy() {
        return new AshlingTheLimitless(this);
    }
}

class AshlingTheLimitlessEffect extends OneShotEffect {

    AshlingTheLimitlessEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it. The token gains haste until end of turn. "
                + "At the beginning of your next end step, sacrifice it unless you pay {W}{U}{B}{R}{G}";
    }

    private AshlingTheLimitlessEffect(final AshlingTheLimitlessEffect effect) {
        super(effect);
    }

    @Override
    public AshlingTheLimitlessEffect copy() {
        return new AshlingTheLimitlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = (Permanent) getValue("sacrificedPermanent");
        if (sacrificedPermanent == null) {
            return false;
        }

        CreateTokenCopyTargetEffect copyEffect = new CreateTokenCopyTargetEffect()
                .setSavedPermanent(sacrificedPermanent);
        if (!copyEffect.apply(game, source)) {
            return false;
        }

        FixedTargets tokenTargets = new FixedTargets(copyEffect.getAddedTokenPermanents(), game);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(tokenTargets), source);

        DoUnlessControllerPaysEffect sacrificeUnlessPaid = new DoUnlessControllerPaysEffect(
                new SacrificeTargetEffect("sacrifice it", source.getControllerId()),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}"),
                "Pay {W}{U}{B}{R}{G}?"
        );
        sacrificeUnlessPaid.setTargetPointer(tokenTargets);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                sacrificeUnlessPaid, TargetController.YOU
        ), source);
        return true;
    }
}

package mage.cards.a;

import mage.MageObject;
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
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshlingTheLimitless extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "Elemental permanent spells you cast from your hand"
    );
    private static final FilterPermanent filter2 = new FilterPermanent(
            "nontoken Elemental"
    );

    static {
        filter.add(SubType.ELEMENTAL.getPredicate());
        filter.add(permanentCardTypePredicate());
        filter.add(new CastFromZonePredicate(Zone.HAND));
        filter2.add(SubType.ELEMENTAL.getPredicate());
        filter2.add(TokenPredicate.FALSE);
    }

    @SuppressWarnings("unchecked")
    private static Predicate<MageObject> permanentCardTypePredicate() {
        return Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.BATTLE.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        );
    }

    public AshlingTheLimitless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Elemental permanent spells you cast from your hand gain evoke {4} as you cast them.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(
                new EvokeAbility("{4}"), filter
        ).setText("Elemental permanent spells you cast from your hand gain evoke {4} as you cast them")));

        // Whenever you sacrifice a nontoken Elemental, create a token that's a copy of it. The token gains haste until end of turn.
        // At the beginning of your next end step, sacrifice it unless you pay {W}{U}{B}{R}{G}.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                Zone.BATTLEFIELD, new AshlingTheLimitlessEffect(), filter2,
                TargetController.YOU, SetTargetPointer.PERMANENT, false
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
        staticText = "create a token that's a copy of it. The token gains haste until end of turn. " +
                "At the beginning of your next end step, sacrifice it unless you pay {W}{U}{B}{R}{G}";
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
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(true).setHasHaste(true);
        effect.setTargetPointer(getTargetPointer().copy());
        if (!effect.apply(game, source) || effect.getAddedPermanents().isEmpty()) {
            return false;
        }
        DoUnlessControllerPaysEffect sacrificeUnlessPaid = new DoUnlessControllerPaysEffect(
                new SacrificeTargetEffect("sacrifice it", source.getControllerId()),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}"),
                "Pay {W}{U}{B}{R}{G}? If you don't, sacrifice the token."
        );
        sacrificeUnlessPaid.setTargetPointer(new FixedTargets(
                new ArrayList<Permanent>(effect.getAddedPermanents()), game
        ));
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                sacrificeUnlessPaid, TargetController.YOU
        ), source);
        return true;
    }
}

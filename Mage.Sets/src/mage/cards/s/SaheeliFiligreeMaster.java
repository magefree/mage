package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.command.emblems.SaheeliFiligreeMasterEmblem;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SaheeliFiligreeMaster extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledArtifactPermanent("an untapped artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public SaheeliFiligreeMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAHEELI);
        this.setStartingLoyalty(3);

        // +1: Scry 1. You may tap an untapped artifact you control. If you do, draw a card.
        Ability ability = new LoyaltyAbility(new ScryEffect(1, false), 1);
        ability.addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new TapTargetCost(new TargetControlledPermanent(filter))
        ));
        this.addAbility(ability);

        // −2: Create two 1/1 colorless Thopter artifact creature tokens with flying. They gain haste until end of turn.
        this.addAbility(new LoyaltyAbility(new SaheeliFiligreeMasterEffect(), -2));

        // −4: You get an emblem with "Artifact creatures you control get +1/+1" and "Artifact spells you cast cost {1} less to cast."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new SaheeliFiligreeMasterEmblem()), -4));
    }

    private SaheeliFiligreeMaster(final SaheeliFiligreeMaster card) {
        super(card);
    }

    @Override
    public SaheeliFiligreeMaster copy() {
        return new SaheeliFiligreeMaster(this);
    }
}

class SaheeliFiligreeMasterEffect extends OneShotEffect {

    SaheeliFiligreeMasterEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create two 1/1 colorless Thopter artifact creature tokens with flying. They gain haste until end of turn";
    }

    private SaheeliFiligreeMasterEffect(final SaheeliFiligreeMasterEffect effect) {
        super(effect);
    }

    @Override
    public SaheeliFiligreeMasterEffect copy() {
        return new SaheeliFiligreeMasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new ThopterColorlessToken();
        token.putOntoBattlefield(2, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}

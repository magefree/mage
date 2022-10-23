package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.*;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierArtifactToken;
import mage.target.common.TargetNonlandPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaPlaneswalker extends MeldCard {

    private static final FilterCard filter = new FilterCard("artifact, instant, and sorcery spells");
    private static final FilterPermanent filter2 = new FilterPermanent("artifacts and planeswalkers");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    public UrzaPlaneswalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.URZA);

        this.setStartingLoyalty(7);

        this.color.setWhite(true);
        this.color.setBlue(true);

        // You may activate the loyalty abilities of Urza, Planeswalker twice each turn rather than only once.
        this.addAbility(new SimpleStaticAbility(new UrzaPlaneswalkerEffect()));

        // +2: Artifact, instant, and sorcery spells you cast this turn cost {2} less to cast. You gain 2 life.
        Ability ability = new LoyaltyAbility(new SpellsCostReductionControllerEffect(filter, 2)
                .setDuration(Duration.EndOfTurn)
                .setText("artifact, instant, and sorcery spells you cast this turn cost {2} less to cast"), 2);
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);

        // +1: Draw two cards, then discard a card.
        this.addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(2, 1), 1));

        // 0: Create two 1/1 colorless Soldier artifact creature tokens.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierArtifactToken(), 2), 0));

        // -3: Exile target nonland permanent.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // -10: Artifacts and planeswalkers you control gain indestructible until end of turn. Destroy all nonland permanents.
        ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter2
        ), -10);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
        this.addAbility(ability);
    }

    private UrzaPlaneswalker(final UrzaPlaneswalker card) {
        super(card);
    }

    @Override
    public UrzaPlaneswalker copy() {
        return new UrzaPlaneswalker(this);
    }
}

class UrzaPlaneswalkerEffect extends ContinuousEffectImpl {

    public UrzaPlaneswalkerEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "once during each of your turns, you may activate an additional loyalty ability of {this}";
    }

    public UrzaPlaneswalkerEffect(final UrzaPlaneswalkerEffect effect) {
        super(effect);
    }

    @Override
    public UrzaPlaneswalkerEffect copy() {
        return new UrzaPlaneswalkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(Permanent::incrementLoyaltyActivationsAvailable);
        return true;
    }
}

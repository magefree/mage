package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrzaLordProtector extends MeldCard {

    private static final FilterCard filter = new FilterCard("artifact, instant, and sorcery spells");
    private static final FilterCard planeswalkerFilter = new FilterCard("Artifact, instant, and sorcery spells you cast this turn");
    private static final FilterPermanent planeswalkerFilter2 = new FilterPermanent("artifacts and planeswalkers");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        planeswalkerFilter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
        planeswalkerFilter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    private static final Condition condition = new MeldCondition(
            "The Mightstone and Weakstone", CardType.ARTIFACT
    );

    public UrzaLordProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ARTIFICER}, "{1}{W}{U}",
                "Urza, Planeswalker",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.URZA}, "WU");

        // Urza, Lord Protector
        this.getLeftHalfCard().setPT(2, 4);

        this.meldsWithClazz = mage.cards.t.TheMightstoneAndWeakstone.class;

        // Artifact, instant, and sorcery spells you cast cost {1} less to cast.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // {7}: If you both own and control Urza, Lord Protector and an artifact named The Mightstone and Weakstone, exile them, then meld them into Urza, Planeswalker. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new ConditionalOneShotEffect(
                new MeldEffect("The Mightstone and Weakstone", "Urza, Planeswalker"),
                condition, "If you both own and control {this} and an artifact named " +
                "The Mightstone and Weakstone, exile them, then meld them into Urza, Planeswalker"
        ), new GenericManaCost(7)));

        // Urza, Planeswalker
        this.getRightHalfCard().setStartingLoyalty(7);

        // You may activate the loyalty abilities of Urza, Planeswalker twice each turn rather than only once.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new UrzaPlaneswalkerEffect()));

        // +2: Artifact, instant, and sorcery spells you cast this turn cost {2} less to cast. You gain 2 life.
        Ability ability = new LoyaltyAbility(
                new SpellsCostReductionControllerEffect(planeswalkerFilter, 2).setDuration(Duration.EndOfTurn), 2);
        ability.addEffect(new GainLifeEffect(2));
        this.getRightHalfCard().addAbility(ability);

        // +1: Draw two cards, then discard a card.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DrawDiscardControllerEffect(2, 1), 1));

        // 0: Create two 1/1 colorless Soldier artifact creature tokens.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierArtifactToken(), 2), 0));

        // -3: Exile target nonland permanent.
        ability = new LoyaltyAbility(new ExileTargetEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.getRightHalfCard().addAbility(ability);

        // -10: Artifacts and planeswalkers you control gain indestructible until end of turn. Destroy all nonland permanents.
        ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, planeswalkerFilter2
        ), -10);
        ability.addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENTS_NON_LAND));
        this.getRightHalfCard().addAbility(ability);
    }

    private UrzaLordProtector(final UrzaLordProtector card) {
        super(card);
    }

    @Override
    public UrzaLordProtector copy() {
        return new UrzaLordProtector(this);
    }
}

class UrzaPlaneswalkerEffect extends ContinuousEffectImpl {

    UrzaPlaneswalkerEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "you may activate the loyalty abilities of {this} twice each turn rather than only once";
    }

    private UrzaPlaneswalkerEffect(final UrzaPlaneswalkerEffect effect) {
        super(effect);
    }

    @Override
    public UrzaPlaneswalkerEffect copy() {
        return new UrzaPlaneswalkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }

        permanent.setLoyaltyActivationsAvailable(2);
        return true;
    }
}

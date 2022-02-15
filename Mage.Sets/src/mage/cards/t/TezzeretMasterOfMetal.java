package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class TezzeretMasterOfMetal extends CardImpl {

    public TezzeretMasterOfMetal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TEZZERET);

        this.setStartingLoyalty(5);

        // +1: Reveal cards from the top of your library until you reveal an artifact card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new RevealCardsFromLibraryUntilEffect(new FilterArtifactCard(), Zone.HAND, Zone.LIBRARY), 1));

        // -3: Target opponent loses life equal to the number of artifacts you control.
        DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent());
        Ability ability = new LoyaltyAbility(new LoseLifeTargetEffect(xValue), -3);
        ability.addTarget(new TargetOpponent());
        ability.addHint(new ValueHint("Artifacts you control", xValue));
        this.addAbility(ability);

        // -8: Gain control of all artifacts and creatures target opponent controls.
        ability = new LoyaltyAbility(new TezzeretMasterOfMetalEffect(), -8);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private TezzeretMasterOfMetal(final TezzeretMasterOfMetal card) {
        super(card);
    }

    @Override
    public TezzeretMasterOfMetal copy() {
        return new TezzeretMasterOfMetal(this);
    }
}

class TezzeretMasterOfMetalEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("artifacts and creatures");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(), CardType.ARTIFACT.getPredicate()));
    }

    public TezzeretMasterOfMetalEffect() {
        super(Outcome.GainControl);
        this.staticText = "Gain control of all artifacts and creatures target opponent controls";
    }

    public TezzeretMasterOfMetalEffect(final TezzeretMasterOfMetalEffect effect) {
        super(effect);
    }

    @Override
    public TezzeretMasterOfMetalEffect copy() {
        return new TezzeretMasterOfMetalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter, targetPointer.getFirst(game, source), game);
        for (Permanent permanent : permanents) {
            ContinuousEffect effect = new TezzeretMasterOfMetalControlEffect(source.getControllerId());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class TezzeretMasterOfMetalControlEffect extends ContinuousEffectImpl {

    private final UUID controllerId;

    public TezzeretMasterOfMetalControlEffect(UUID controllerId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllerId = controllerId;
    }

    public TezzeretMasterOfMetalControlEffect(final TezzeretMasterOfMetalControlEffect effect) {
        super(effect);
        this.controllerId = effect.controllerId;
    }

    @Override
    public TezzeretMasterOfMetalControlEffect copy() {
        return new TezzeretMasterOfMetalControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null && controllerId != null) {
            return permanent.changeControllerId(controllerId, game, source);
        }
        return false;
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author nantuko
 */
public final class AdaptiveAutomaton extends CardImpl {

    private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control of the chosen type");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public AdaptiveAutomaton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.CONSTRUCT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As Adaptive Automaton enters the battlefield, choose a creature type.
        // Adaptive Automaton is the chosen type in addition to its other types.
        AsEntersBattlefieldAbility ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature), null, EnterEventType.SELF);
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AdaptiveAutomatonAddSubtypeEffect()));
        // Other creatures you control of the chosen type get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllOfChosenSubtypeEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    public AdaptiveAutomaton(final AdaptiveAutomaton card) {
        super(card);
    }

    @Override
    public AdaptiveAutomaton copy() {
        return new AdaptiveAutomaton(this);
    }
}

class AdaptiveAutomatonAddSubtypeEffect extends ContinuousEffectImpl {

    public AdaptiveAutomatonAddSubtypeEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} is the chosen type in addition to its other types";
    }

    public AdaptiveAutomatonAddSubtypeEffect(final AdaptiveAutomatonAddSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            SubType subType = ChooseCreatureTypeEffect.getChoosenCreatureType(permanent.getId(), game);
            if (subType != null && !permanent.hasSubtype(subType, game)) {
                permanent.getSubtype(game).add(subType);
            }
        }
        return true;
    }

    @Override
    public AdaptiveAutomatonAddSubtypeEffect copy() {
        return new AdaptiveAutomatonAddSubtypeEffect(this);
    }
}

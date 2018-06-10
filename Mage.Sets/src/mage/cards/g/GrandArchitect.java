
package mage.cards.g;

import java.util.UUID;
import mage.*;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com, nantuko
 */
public final class GrandArchitect extends CardImpl {

    private static final FilterCreaturePermanent boostFilter = new FilterCreaturePermanent("blue creatures");
    private static final FilterCreaturePermanent targetFilter = new FilterCreaturePermanent("artifact creature");

    static {
        boostFilter.add(new ColorPredicate(ObjectColor.BLUE));
        targetFilter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public GrandArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, boostFilter, true)));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrandArchitectEffect(), new ManaCostsImpl("{U}"));
        ability.addTarget(new TargetPermanent(targetFilter));
        this.addAbility(ability);
        this.addAbility(new GrandArchitectManaAbility());
    }

    public GrandArchitect(final GrandArchitect card) {
        super(card);
    }

    @Override
    public GrandArchitect copy() {
        return new GrandArchitect(this);
    }

}

class GrandArchitectEffect extends ContinuousEffectImpl {

    public GrandArchitectEffect() {
        super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Detriment);
        staticText = "Target artifact creature becomes blue until end of turn";
    }

    public GrandArchitectEffect(final GrandArchitectEffect effect) {
        super(effect);
    }

    @Override
    public GrandArchitectEffect copy() {
        return new GrandArchitectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.getColor(game).setRed(false);
            permanent.getColor(game).setWhite(false);
            permanent.getColor(game).setGreen(false);
            permanent.getColor(game).setBlue(true);
            permanent.getColor(game).setBlack(false);
            return true;
        }
        return false;
    }

}

class GrandArchitectManaAbility extends ActivatedManaAbilityImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
        filter.add(Predicates.not(new TappedPredicate()));
    }

    GrandArchitectManaAbility() {
        super(Zone.BATTLEFIELD, new BasicManaEffect(new GrandArchitectConditionalMana()), new TapTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, true)));
        this.netMana.add(Mana.ColorlessMana(2));
    }

    GrandArchitectManaAbility(GrandArchitectManaAbility ability) {
        super(ability);
    }

    @Override
    public GrandArchitectManaAbility copy() {
        return new GrandArchitectManaAbility(this);
    }
}

class GrandArchitectConditionalMana extends ConditionalMana {

    public GrandArchitectConditionalMana() {
        super(Mana.ColorlessMana(2));
        staticText = "Spend this mana only to cast artifact spells or activate abilities of artifacts";
        addCondition(new GrandArchitectManaCondition());
    }
}

class GrandArchitectManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getSourceId());
        if (object != null && object.isArtifact()) {
            return true;
        }
        return false;
    }
}

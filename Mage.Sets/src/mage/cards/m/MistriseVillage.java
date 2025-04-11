package mage.cards.m;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.continuous.NextSpellCastHasAbilityEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author Jmlundeen
 */
public final class MistriseVillage extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain or a Forest");

    static {
        filter.add(Predicates.or(SubType.MOUNTAIN.getPredicate(), SubType.FOREST.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public MistriseVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // This land enters tapped unless you control a Mountain or a Forest.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {U}, {T}: The next spell you cast this turn can't be countered.
        Effect effect = new AddContinuousEffectToGame(new MistriseCantBeCounteredEffect());
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MistriseVillage(final MistriseVillage card) {
        super(card);
    }

    @Override
    public MistriseVillage copy() {
        return new MistriseVillage(this);
    }
}

class MistriseCantBeCounteredEffect extends ContinuousRuleModifyingEffectImpl {

    public MistriseCantBeCounteredEffect() {
        super(Duration.OneUse, Outcome.Benefit, false, true);
        staticText = "the next spell you cast this turn can't be countered";
    }

    protected MistriseCantBeCounteredEffect(final MistriseCantBeCounteredEffect effect) {
        super(effect);
    }

    @Override
    public MistriseCantBeCounteredEffect copy() {
        return new MistriseCantBeCounteredEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        StackObject sourceObject = game.getStack().getStackObject(event.getSourceId());
        StackObject targetObject = game.getStack().getStackObject(event.getTargetId());
        if (sourceObject != null && targetObject != null) {
            return targetObject.getName() + " cannot be countered by " + sourceObject.getName();
        }
        return staticText;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        boolean res = spell != null && spell.isControlledBy(source.getControllerId());
        if (res) {
            discard();
        }
        return res;
    }
}

package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.SpellsCastWatcher;

import java.util.List;
import java.util.UUID;

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

class MistriseCantBeCounteredEffect extends ContinuousEffectImpl {

    private int spellsCastThisTurn;

    public MistriseCantBeCounteredEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "the next spell you cast this turn can't be countered";
    }

    protected MistriseCantBeCounteredEffect(final MistriseCantBeCounteredEffect effect) {
        super(effect);
        this.spellsCastThisTurn = effect.spellsCastThisTurn;
    }

    @Override
    public MistriseCantBeCounteredEffect copy() {
        return new MistriseCantBeCounteredEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            spellsCastThisTurn = watcher.getSpellsCastThisTurn(source.getControllerId()).size();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher == null) {
            return false;
        }
        if (game.getStack().isEmpty() && watcher.getSpellsCastThisTurn(source.getControllerId()).size() >= spellsCastThisTurn + 1) {
            discard();
            return false;
        }
        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell) || !stackObject.isControlledBy(source.getControllerId())) {
                continue;
            }
            Spell spell = (Spell) stackObject;

            List<Spell> spellsCast = watcher.getSpellsCastThisTurn(source.getControllerId());
            for (int i = 0; i < spellsCast.size(); i++) {
                if (i == spellsCastThisTurn && spellsCast.get(i).getId().equals(spell.getId())) {
                    game.getState().addOtherAbility(spell.getCard(), new CantBeCounteredSourceAbility());
                    return true;
                }
            }
        }
        return false;
    }
}

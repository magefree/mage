package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.watchers.common.SpellsCastWatcher;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;

/**
 *
 * @author muz
 */
public final class TheoristsProxy extends CardImpl {

    public TheoristsProxy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this creature enters, empower Jace 3.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(3)));

        // {U}, Sacrifice this creature: The next spell you cast this turn can't be countered.
        Effect effect = new AddContinuousEffectToGame(new TheoristsProxyEffect());
        Ability ability = new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private TheoristsProxy(final TheoristsProxy card) {
        super(card);
    }

    @Override
    public TheoristsProxy copy() {
        return new TheoristsProxy(this);
    }
}

class TheoristsProxyEffect extends ContinuousEffectImpl {

    private int spellsCastThisTurn;

    public TheoristsProxyEffect() {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "the next spell you cast this turn can't be countered";
    }

    protected TheoristsProxyEffect(final TheoristsProxyEffect effect) {
        super(effect);
        this.spellsCastThisTurn = effect.spellsCastThisTurn;
    }

    @Override
    public TheoristsProxyEffect copy() {
        return new TheoristsProxyEffect(this);
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

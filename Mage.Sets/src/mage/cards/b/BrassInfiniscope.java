package mage.cards.b;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.delayed.CastNextSpellDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class BrassInfiniscope extends CardImpl {

    public BrassInfiniscope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}: Add {C}{C}. When you next cast a spell with {X} in its mana cost this turn, you draw a card and gain half X life, rounded down.
        SimpleManaAbility manaAbility = new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new BrassInfiniscopeManaEffect(),
                new TapSourceCost()
        );
        manaAbility.setUndoPossible(false);
        this.addAbility(manaAbility);
    }

    private BrassInfiniscope(final BrassInfiniscope card) {
        super(card);
    }

    @Override
    public BrassInfiniscope copy() {
        return new BrassInfiniscope(this);
    }
}

class BrassInfiniscopeManaEffect extends ManaEffect {

    private static final FilterSpell filter = new FilterSpell("a spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public BrassInfiniscopeManaEffect() {
        super();
        this.staticText = "add {C}{C}. When you next cast a spell with {X} in its mana cost this turn, you draw a card and gain half X life, rounded down";
    }

    private BrassInfiniscopeManaEffect(final BrassInfiniscopeManaEffect effect) {
        super(effect);
    }

    @Override
    public BrassInfiniscopeManaEffect copy() {
        return new BrassInfiniscopeManaEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        return Mana.ColorlessMana(2);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = super.apply(game, source);
        if (!game.inCheckPlayableState()) {
            game.addDelayedTriggeredAbility(
                    new CastNextSpellDelayedTriggeredAbility(new BrassInfiniscopeDelayedEffect(), filter, false),
                    source
            );
        }
        return result;
    }
}

class BrassInfiniscopeDelayedEffect extends OneShotEffect {

    BrassInfiniscopeDelayedEffect() {
        super(Outcome.Benefit);
        this.staticText = "you draw a card and gain half X life, rounded down";
    }

    private BrassInfiniscopeDelayedEffect(final BrassInfiniscopeDelayedEffect effect) {
        super(effect);
    }

    @Override
    public BrassInfiniscopeDelayedEffect copy() {
        return new BrassInfiniscopeDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (player == null || spell == null) {
            return false;
        }
        player.drawCards(1, source, game);
        int xValue = CardUtil.getSourceCostsTag(game, spell.getSpellAbility(), "X", 0);
        player.gainLife(xValue / 2, game, source);
        return true;
    }
}

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.MerfolkToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.watchers.common.ManaPaidSourceWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class GrandmotherGoby extends CardImpl {

    public GrandmotherGoby(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, counter that spell. Create a number of 1/1 blue Merfolk creature tokens equal to the amount of mana spent to cast that spell.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new CounterTargetEffect(),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE,
            false,
            SetTargetPointer.SPELL
        );
        ability.addEffect(new GrandmotherGobyEffect());
        this.addAbility(ability);
    }

    private GrandmotherGoby(final GrandmotherGoby card) {
        super(card);
    }

    @Override
    public GrandmotherGoby copy() {
        return new GrandmotherGoby(this);
    }
}

class GrandmotherGobyEffect extends OneShotEffect {

    public GrandmotherGobyEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create a number of 1/1 blue Merfolk creature tokens equal to the amount of mana spent to cast that spell";
    }

    private GrandmotherGobyEffect(final GrandmotherGobyEffect effect) {
        super(effect);
    }

    @Override
    public GrandmotherGobyEffect copy() {
        return new GrandmotherGobyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) getValue("spellCast");
        if (controller == null || spell == null) {
            return false;
        }
        int manaPaid = ManaPaidSourceWatcher.getTotalPaid(spell.getId(), game);
        if (manaPaid > 0) {
            return new CreateTokenEffect(new MerfolkToken(), manaPaid).apply(game, source);
        }
        return true;
    }
}

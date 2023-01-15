package mage.cards.o;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianGoblinToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTargets;

/**
 * @author TheElk801
 */
public final class OvikaEnigmaGoliath extends CardImpl {

    public OvikaEnigmaGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward--{3}, Pay 3 life.
        this.addAbility(new WardAbility(new CompositeCost(
                new GenericManaCost(2), new PayLifeCost(3), "{3}, Pay 3 life"
        )));

        // Whenever you cast a noncreature spell, create X 1/1 red Phyrexian Goblin creature tokens, where X is the mana value of that spell. They gain haste until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new OvikaEnigmaGoliathEffect(), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private OvikaEnigmaGoliath(final OvikaEnigmaGoliath card) {
        super(card);
    }

    @Override
    public OvikaEnigmaGoliath copy() {
        return new OvikaEnigmaGoliath(this);
    }
}

class OvikaEnigmaGoliathEffect extends OneShotEffect {

    OvikaEnigmaGoliathEffect() {
        super(Outcome.Benefit);
        staticText = "create X 1/1 red Phyrexian Goblin creature tokens, " +
                "where X is the mana value of that spell. They gain haste until end of turn";
    }

    private OvikaEnigmaGoliathEffect(final OvikaEnigmaGoliathEffect effect) {
        super(effect);
    }

    @Override
    public OvikaEnigmaGoliathEffect copy() {
        return new OvikaEnigmaGoliathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null || spell.getManaValue() < 1) {
            return false;
        }
        Token token = new PhyrexianGoblinToken();
        token.putOntoBattlefield(spell.getManaValue(), game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        return true;
    }
}

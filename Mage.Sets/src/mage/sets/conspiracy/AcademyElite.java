package mage.sets.conspiracy;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;

/**
 *
 * @author andyfries
 */

public class AcademyElite extends CardImpl {
    public AcademyElite(UUID ownerId) {
        super(ownerId, 20, "Academy Elite", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Academy Elite enters the battlefield with X +1/+1 counters on it, where X is the number of instant and
        // sorcery cards in all graveyards.
        this.addAbility(new EntersBattlefieldAbility(new AcademyEliteEffect1(), " with X +1/+1 counters on it, where X is the number of instant and sorcery cards in all graveyards"));

        // {2}{U}, Remove a +1/+1 counter from Academy Elite: Draw a card, then discard a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawDiscardControllerEffect(1, 1, false), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance(1)));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public AcademyElite(final AcademyElite card) {
        super(card);
    }

    @Override
    public AcademyElite copy() {
        return new AcademyElite(this);
    }

    class AcademyEliteEffect1 extends OneShotEffect {

        public AcademyEliteEffect1() {
            super(Outcome.BoostCreature);
            staticText = "{this} enters the battlefield with X +1/+1 counters on it, where X is the number of instant and sorcery cards in all graveyards";
        }

        public AcademyEliteEffect1(final AcademyEliteEffect1 effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                Object obj = getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
                if (obj != null && obj instanceof SpellAbility) {
                    CardsInAllGraveyardsCount instantsAndSorceries = new CardsInAllGraveyardsCount(new FilterInstantOrSorceryCard("instant or sorcery cards"));
                    int instantsAndSorceriesCount = instantsAndSorceries.calculate(game, source, this);
                    if (instantsAndSorceriesCount > 0) {
                        permanent.addCounters(CounterType.P1P1.createInstance(instantsAndSorceriesCount), game);
                    }
                }
            }
            return true;
        }

        @Override
        public AcademyEliteEffect1 copy() {
            return new AcademyEliteEffect1(this);
        }
    }
}

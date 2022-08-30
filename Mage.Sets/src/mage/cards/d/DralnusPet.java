package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LoneFox
 * @author LevelX
 */
public final class DralnusPet extends CardImpl {

    public DralnusPet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker-{2}{B}, Discard a creature card.
        Costs<Cost> kickerCosts = new CostsImpl<>();
        kickerCosts.add(new ManaCostsImpl<>("{2}{B}"));
        kickerCosts.add(new DiscardCardCost(StaticFilters.FILTER_CARD_CREATURE));
        this.addAbility(new KickerAbility(kickerCosts));
        // If Dralnu's Pet was kicked, it enters the battlefield with flying and with X +1/+1 counters on it, where X is the discarded card's converted mana cost.
        Ability ability = new EntersBattlefieldAbility(new DralnusPetEffect(), KickedCondition.ONCE,
                "If {this} was kicked, it enters the battlefield with flying and with X +1/+1 counters on it, where X is the discarded card's mana value.", "");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private DralnusPet(final DralnusPet card) {
        super(card);
    }

    @Override
    public DralnusPet copy() {
        return new DralnusPet(this);
    }
}

class DralnusPetEffect extends OneShotEffect {

    public DralnusPetEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "and with X +1/+1 counters on it, where X is the discarded card's mana value";
    }

    public DralnusPetEffect(final DralnusPetEffect effect) {
        super(effect);
    }

    @Override
    public DralnusPetEffect copy() {
        return new DralnusPetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null && source.getAbilityType() == AbilityType.STATIC) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        if (controller != null && permanent != null) {
            SpellAbility spellAbility = (SpellAbility) getValue(EntersBattlefieldEffect.SOURCE_CAST_SPELL_ABILITY);
            if (spellAbility != null
                    && spellAbility.getSourceId().equals(source.getSourceId())
                    && permanent.getZoneChangeCounter(game) == spellAbility.getSourceObjectZoneChangeCounter()) {
                int cmc = 0;
                for (Cost cost : spellAbility.getCosts()) {
                    if (cost instanceof DiscardCardCost && !((DiscardCardCost) cost).getCards().isEmpty()) {
                        cmc = ((DiscardCardCost) cost).getCards().get(0).getManaValue();
                    }
                    if (cmc > 0) {
                        return new AddCountersSourceEffect(CounterType.P1P1.createInstance(cmc), true).apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

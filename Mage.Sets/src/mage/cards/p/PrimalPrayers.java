package mage.cards.p;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PrimalPrayers extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public PrimalPrayers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");

        // When Primal Prayers enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // You may cast creature spells with mana value 3 or less by paying {E} rather than paying their mana costs. If you cast a spell this way, you may cast it as though it had flash.
        Ability ability = new SimpleStaticAbility(new PrimalPrayersCastEffect());
        ability.setIdentifier(MageIdentifier.PrimalPrayersAlternateCast); // Is the link allowing the Flash part to only affect that Alternative Cast
        Effect effect = new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, false);
        effect.setText("and as though they had flash");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private PrimalPrayers(final PrimalPrayers card) {
        super(card);
    }

    @Override
    public PrimalPrayers copy() {
        return new PrimalPrayers(this);
    }
}

// Very close to Aluren
class PrimalPrayersCastEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility;

    public PrimalPrayersCastEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "you may cast creature cards with mana value 3 or less by paying {E} rather than paying their mana costs";
        alternativeCastingCostAbility = new AlternativeCostSourceAbility(
                new PayEnergyCost(1), SourceIsSpellCondition.instance, null, filter, true
        );
        alternativeCastingCostAbility.setIdentifier(MageIdentifier.PrimalPrayersAlternateCast);
    }

    private PrimalPrayersCastEffect(final PrimalPrayersCastEffect effect) {
        super(effect);
        this.alternativeCastingCostAbility = effect.alternativeCastingCostAbility.copy();
    }

    @Override
    public PrimalPrayersCastEffect copy() {
        return new PrimalPrayersCastEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
        controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
        return true;
    }
}
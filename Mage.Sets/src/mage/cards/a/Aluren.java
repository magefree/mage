package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * 10/4/2004 	The mana cost of the creatures being cast is still the stated cost on the card,
 * even though you did not pay the cost.
 * 10/4/2004 	Aluren checks the actual printed cost on the creature card, and is not affected
 * by things which allow you to cast the spell for less.
 * 10/4/2004 	You can't choose to cast a creature as though it had flash via Aluren and still pay the mana cost.
 * You either cast the creature normally, or via Aluren without paying the mana cost.
 * 10/4/2004 	You can't use Aluren when casting a creature using another alternate means,
 * such as the Morph ability.
 * 8/1/2008 	If creature with X in its cost is cast this way, X can only be 0.
 *
 * @author emerald000
 */
public final class Aluren extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public Aluren(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{G}");


        // Any player may play creature cards with converted mana cost 3 or less without paying their mana cost
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new AlurenRuleEffect());
        // and as though they had flash.
        // TODO: This as thought effect may only be used if the creature is cast by the aluren effect
        Effect effect = new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, true);
        effect.setText("and as though they had flash");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private Aluren(final Aluren card) {
        super(card);
    }

    @Override
    public Aluren copy() {
        return new Aluren(this);
    }
}

class AlurenRuleEffect extends ContinuousEffectImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(null, SourceIsSpellCondition.instance, null, filter, true);

    public AlurenRuleEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Any player may cast creature cards with mana value 3 or less without paying their mana cost";
    }

    private AlurenRuleEffect(final AlurenRuleEffect effect) {
        super(effect);
    }

    @Override
    public AlurenRuleEffect copy() {
        return new AlurenRuleEffect(this);
    }

    @Override
    public void init(Ability source, Game game, UUID activePlayerId) {
        super.init(source, game, activePlayerId);
        alternativeCastingCostAbility.setSourceId(source.getSourceId());
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
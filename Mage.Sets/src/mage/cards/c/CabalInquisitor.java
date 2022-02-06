
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */
public final class CabalInquisitor extends CardImpl {

    public CabalInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Threshold - {1}{B}, {T}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.
        Ability ability = new ActivateAsSorceryConditionalActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl("{1}{B}"), new CardsInControllerGraveyardCondition(7));
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private CabalInquisitor(final CabalInquisitor card) {
        super(card);
    }

    @Override
    public CabalInquisitor copy() {
        return new CabalInquisitor(this);
    }
}

class ActivateAsSorceryConditionalActivatedAbility extends ActivatedAbilityImpl {

    private static final Effects emptyEffects = new Effects();

    public ActivateAsSorceryConditionalActivatedAbility(Zone zone, Effect effect, ManaCosts cost, Condition condition) {
        super(zone, effect, cost);
        this.condition = condition;
        timing = TimingRule.SORCERY;
    }

    public ActivateAsSorceryConditionalActivatedAbility(final ActivateAsSorceryConditionalActivatedAbility ability) {
        super(ability);
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public ActivateAsSorceryConditionalActivatedAbility copy() {
        return new ActivateAsSorceryConditionalActivatedAbility(this);
    }

    @Override
    public String getRule() {
        return super.getRule() + " Activate only as a sorcery and only if seven or more cards are in your graveyard.";
    }
}

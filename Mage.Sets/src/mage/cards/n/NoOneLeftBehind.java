package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class NoOneLeftBehind extends CardImpl {

    public NoOneLeftBehind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // This spell costs {3} less to cast if it targets a creature card with mana value 3 or less.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, NoOneLeftBehindCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return target creature card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private NoOneLeftBehind(final NoOneLeftBehind card) {
        super(card);
    }

    @Override
    public NoOneLeftBehind copy() {
        return new NoOneLeftBehind(this);
    }
}

enum NoOneLeftBehindCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject sourceSpell = game.getStack().getStackObject(source.getSourceId());
        if (sourceSpell == null) {
            return false;
        }
        for (Target target : sourceSpell.getStackAbility().getTargets()) {
            for (UUID targetId : target.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null && card.isCreature(game) && card.getManaValue() <= 3) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "it targets a creature card with mana value 3 or less";
    }
}

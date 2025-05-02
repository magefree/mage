package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevokeDemise extends CardImpl {

    public RevokeDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // This spell costs {2} less to cast if it targets a creature card with mana value 3 or less.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, RevokeDemiseCondition.instance).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Return target creature card from your graveyard to the battlefield. You gain 2 life.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
    }

    private RevokeDemise(final RevokeDemise card) {
        super(card);
    }

    @Override
    public RevokeDemise copy() {
        return new RevokeDemise(this);
    }
}

enum RevokeDemiseCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        StackObject stackObject = game.getStack().getStackObject(source.getSourceId());
        if (stackObject == null) {
            return false;
        }
        return CardUtil
                .getAllSelectedTargets(stackObject.getStackAbility(), game)
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.isCreature(game) && p.getManaValue() <= 3);
    }

    @Override
    public String toString() {
        return "it targets a creature card with mana value 3 or less";
    }
}

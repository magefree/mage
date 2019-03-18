
package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.cost.SourceCostReductionForEachCardInGraveyardEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NemesisOfMortals extends CardImpl {

    public NemesisOfMortals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Nemesis of Mortals costs {1} less to cast for each creature card in your graveyard.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new SourceCostReductionForEachCardInGraveyardEffect(new FilterCreatureCard()));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // {7}{G}{G}: Monstrosity 5.  This ability costs {1} less to activate for each creature card in your graveyard.
        // TODO: Make ability properly copiable
        ability = new MonstrosityAbility("{7}{G}{G}", 5);
        for (Effect effect : ability.getEffects()) {
            effect.setText("Monstrosity 5.  This ability costs {1} less to activate for each creature card in your graveyard");
        }
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new NemesisOfMortalsCostReducingEffect(ability.getOriginalId())));
    }

    private NemesisOfMortals(final NemesisOfMortals card) {
        super(card);
    }

    @Override
    public NemesisOfMortals copy() {
        return new NemesisOfMortals(this);
    }
}

class NemesisOfMortalsCostReducingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    NemesisOfMortalsCostReducingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.originalId = originalId;
    }

    private NemesisOfMortalsCostReducingEffect(final NemesisOfMortalsCostReducingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CardUtil.reduceCost(abilityToModify, controller.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game));
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public NemesisOfMortalsCostReducingEffect copy() {
        return new NemesisOfMortalsCostReducingEffect(this);
    }

}

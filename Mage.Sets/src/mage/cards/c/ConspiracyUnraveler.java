package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ConspiracyUnraveler extends CardImpl {

    public ConspiracyUnraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may collect evidence 10 rather than pay the mana cost for spells that you cast.
        this.addAbility(new SimpleStaticAbility(new ConspiracyUnravelerInsteadEffect()));
    }

    private ConspiracyUnraveler(final ConspiracyUnraveler card) {
        super(card);
    }

    @Override
    public ConspiracyUnraveler copy() {
        return new ConspiracyUnraveler(this);
    }
}

// Inspired by WUBRGInsteadEffect
class ConspiracyUnravelerInsteadEffect extends ContinuousEffectImpl {

    private final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(new CollectEvidenceCost(10), SourceIsSpellCondition.instance);

    ConspiracyUnravelerInsteadEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You may collect evidence 10 rather than pay the mana cost for spells that you cast";
    }

    protected ConspiracyUnravelerInsteadEffect(final ConspiracyUnravelerInsteadEffect effect) {
        super(effect);
    }

    @Override
    public ConspiracyUnravelerInsteadEffect copy() {
        return new ConspiracyUnravelerInsteadEffect(this);
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
            controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
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

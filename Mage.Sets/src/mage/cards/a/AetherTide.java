package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author jeffwadsworth
 */
public final class AetherTide extends CardImpl {

    public AetherTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}");

        // As an additional cost to cast Aether Tide, discard X creature cards.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new InfoEffect("As an additional cost to cast this spell, discard X creature cards"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Return X target creatures to their owners' hands.
        Effect effect = new ReturnToHandTargetEffect(true);
        effect.setText("Return X target creatures to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(AetherTideTargetAdjuster.instance);
        this.getSpellAbility().setCostAdjuster(AetherTideCostAdjuster.instance);

    }

    private AetherTide(final AetherTide card) {
        super(card);
    }

    @Override
    public AetherTide copy() {
        return new AetherTide(this);
    }
}

enum AetherTideTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetCreaturePermanent(xValue, xValue, new FilterCreaturePermanent(), false));
    }
}

enum AetherTideCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(xValue, xValue, new FilterCreatureCard("creature cards"))));
        }
    }
}

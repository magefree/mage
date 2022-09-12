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
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

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
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target creatures to their owners' hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setTargetAdjuster(XTargetsAdjuster.instance);
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

enum AetherTideCostAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        if (xValue > 0) {
            ability.addCost(new DiscardTargetCost(new TargetCardInHand(xValue, xValue, StaticFilters.FILTER_CARD_CREATURES)));
        }
    }
}

package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardXTargetCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NostalgicDreams extends CardImpl {

    public NostalgicDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{G}");

        // As an additional cost to cast Nostalgic Dreams, discard X cards.
        this.getSpellAbility().addCost(new DiscardXTargetCost(new FilterCard("cards"), true));

        // Return X target cards from your graveyard to your hand.
        Effect effect = new ReturnFromGraveyardToHandTargetEffect();
        effect.setText("Return X target cards from your graveyard to your hand");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().setTargetAdjuster(NostalgicDreamsAdjuster.instance);

        // Exile Nostalgic Dreams.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private NostalgicDreams(final NostalgicDreams card) {
        super(card);
    }

    @Override
    public NostalgicDreams copy() {
        return new NostalgicDreams(this);
    }
}

enum NostalgicDreamsAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(
                GetXValue.instance.calculate(game, ability, null),
                StaticFilters.FILTER_CARD_FROM_YOUR_GRAVEYARD
        ));
    }
}
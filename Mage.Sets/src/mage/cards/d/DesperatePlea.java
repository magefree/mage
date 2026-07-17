package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.SacrificeCostCreaturesPower;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DesperatePlea extends CardImpl {

    public DesperatePlea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        this.subtype.add(SubType.LESSON);

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Return target creature card from your graveyard to the battlefield if its power is less than or equal to the sacrificed creature's power.
        this.getSpellAbility().addEffect(new DesperatePleaEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // * Destroy target creature.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect()).addTarget(new TargetCreaturePermanent()));
    }

    private DesperatePlea(final DesperatePlea card) {
        super(card);
    }

    @Override
    public DesperatePlea copy() {
        return new DesperatePlea(this);
    }
}

class DesperatePleaEffect extends OneShotEffect {

    DesperatePleaEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature card from your graveyard to the battlefield " +
                "if its power is less than or equal to the sacrificed creature's power";
    }

    private DesperatePleaEffect(final DesperatePleaEffect effect) {
        super(effect);
    }

    @Override
    public DesperatePleaEffect copy() {
        return new DesperatePleaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        return player != null && card != null
                && card.getPower().getValue() <= SacrificeCostCreaturesPower.instance.calculate(game, source, this)
                && player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}

package mage.cards.c;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.mana.AddConditionalManaEffect;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.ChandraChillOfComplianceEmblem;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class ChandraChillOfCompliance extends CardImpl {

    public ChandraChillOfCompliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CHANDRA);

        this.setStartingLoyalty(3);

        // +1: Surveil 1. If you put a noncreature, nonland card into your graveyard this way, put that card into your hand.
        this.addAbility(new LoyaltyAbility(new ChandraChillOfComplianceSurveilEffect(), 1));

        // +1: Add {U}. Spend this mana only to cast a noncreature spell.
        this.addAbility(new LoyaltyAbility(new AddConditionalManaEffect(Mana.BlueMana(1), new ConditionalSpellManaBuilder(StaticFilters.FILTER_SPELL_A_NON_CREATURE)), 1));

        // −X: Tap target artifact or creature. Put X stun counters on it.
        LoyaltyAbility ability = new LoyaltyAbility(new TapTargetEffect());
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(), GetXValue.instance)
            .setText("Put X stun counters on it"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // −6: You get an emblem with "Whenever you cast a spell, draw a card."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ChandraChillOfComplianceEmblem()), -6));
    }

    private ChandraChillOfCompliance(final ChandraChillOfCompliance card) {
        super(card);
    }

    @Override
    public ChandraChillOfCompliance copy() {
        return new ChandraChillOfCompliance(this);
    }

}

class ChandraChillOfComplianceSurveilEffect extends OneShotEffect {

    ChandraChillOfComplianceSurveilEffect() {
        super(Outcome.Benefit);
        this.staticText = "surveil 1. If you put a noncreature, nonland card into your graveyard this way, put that card into your hand";
    }

    private ChandraChillOfComplianceSurveilEffect(final ChandraChillOfComplianceSurveilEffect effect) {
        super(effect);
    }

    @Override
    public ChandraChillOfComplianceSurveilEffect copy() {
        return new ChandraChillOfComplianceSurveilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<UUID> graveyardBefore = new HashSet<>(player.getGraveyard());
        Player.SurveilResult result = player.doSurveil(1, source, game);
        if (!result.hasSurveilled() || result.getNumberPutInGraveyard() == 0) {
            return result.hasSurveilled();
        }
        for (Card card : player.getGraveyard().getCards(game)) {
            if (!graveyardBefore.contains(card.getId())
                    && !card.isCreature(game)
                    && !card.isLand(game)) {
                player.moveCards(card, Zone.HAND, source, game);
            }
        }
        return true;
    }
}

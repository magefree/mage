package mage.cards.m;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MegatonsFate extends CardImpl {

    public MegatonsFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}");

        // Choose one --
        // * Disarm -- Destroy target artifact. Create four Treasure tokens.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new TreasureToken(), 4));
        this.getSpellAbility().withFirstModeFlavorWord("Disarm");

        // * Detonate -- Megaton's Fate deals 8 damage to each creature. Each player gets four rad counters.
        this.getSpellAbility().addMode(new Mode(new DamageAllEffect(8, StaticFilters.FILTER_PERMANENT_CREATURE))
                .addEffect(new AddCountersPlayersEffect(CounterType.RAD.createInstance(4), TargetController.EACH_PLAYER))
                .withFlavorWord("Detonate"));
    }

    private MegatonsFate(final MegatonsFate card) {
        super(card);
    }

    @Override
    public MegatonsFate copy() {
        return new MegatonsFate(this);
    }
}

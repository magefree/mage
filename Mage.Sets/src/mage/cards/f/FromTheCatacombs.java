package mage.cards.f;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.common.replacement.LeaveBattlefieldExileTargetReplacementEffect;
import mage.abilities.hint.common.InitiativeHint;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FromTheCatacombs extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from a graveyard");

    public FromTheCatacombs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Return target creature card from a graveyard to the battlefield with a corpse counter on it. If that creature would leave the battlefield, exile it instead of putting it anywhere else.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldWithCounterTargetEffect(CounterType.CORPSE.createInstance()));
        this.getSpellAbility().addEffect(new LeaveBattlefieldExileTargetReplacementEffect("that creature"));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));

        // You take the initiative.
        this.getSpellAbility().addEffect(new TakeTheInitiativeEffect().concatBy("<br>"));
        this.getSpellAbility().addHint(InitiativeHint.instance);

        // Escape—{3}{B}{B}, Exile four other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{3}{B}{B}", 5));
    }

    private FromTheCatacombs(final FromTheCatacombs card) {
        super(card);
    }

    @Override
    public FromTheCatacombs copy() {
        return new FromTheCatacombs(this);
    }
}

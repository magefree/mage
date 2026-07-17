package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CreatureLeftThisTurnCondition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.watchers.common.CreatureLeftBattlefieldWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TaleOfMomo extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("an Ally creature card");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public TaleOfMomo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}");

        // This spell costs {2} less to cast if a creature left the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(
                new SpellCostReductionSourceEffect(2, CreatureLeftThisTurnCondition.instance)
        ).addHint(CreatureLeftThisTurnCondition.getHint()), new CreatureLeftBattlefieldWatcher());

        // Search your library and/or graveyard for an Ally creature card, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryGraveyardPutInHandEffect(filter));
    }

    private TaleOfMomo(final TaleOfMomo card) {
        super(card);
    }

    @Override
    public TaleOfMomo copy() {
        return new TaleOfMomo(this);
    }
}

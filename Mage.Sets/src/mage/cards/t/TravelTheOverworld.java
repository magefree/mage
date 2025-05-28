package mage.cards.t;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TravelTheOverworld extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.TOWN, "Towns");
    private static final Hint hint = new ValueHint("Towns you control", new PermanentsOnBattlefieldCount(filter));

    public TravelTheOverworld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}{U}");

        // Affinity for Towns
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).setRuleAtTheTop(true).addHint(hint));

        // Draw four cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(4));
    }

    private TravelTheOverworld(final TravelTheOverworld card) {
        super(card);
    }

    @Override
    public TravelTheOverworld copy() {
        return new TravelTheOverworld(this);
    }
}

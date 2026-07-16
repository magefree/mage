package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KingpinWilsonFisk extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanentThisOrAnother(
            StaticFilters.FILTER_PERMANENT_CREATURE,
            true, "{this} or another creature"
    );

    public KingpinWilsonFisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you sacrifice Kingpin or another creature, create two Treasure tokens. This ability triggers only once each turn.
        this.addAbility(new SacrificePermanentTriggeredAbility(
            new CreateTokenEffect(new TreasureToken(), 2), filter
        ).setTriggersLimitEachTurn(1));
    }

    private KingpinWilsonFisk(final KingpinWilsonFisk card) {
        super(card);
    }

    @Override
    public KingpinWilsonFisk copy() {
        return new KingpinWilsonFisk(this);
    }
}

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.SpellOrTargetsPermanentPredicate;

/**
 *
 * @author muz
 */
public final class DanithaSwordOfHope extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an Equipment spell or a spell that targets a creature you control");

    static {
        filter.add(new SpellOrTargetsPermanentPredicate(
            SubType.EQUIPMENT, StaticFilters.FILTER_CONTROLLED_CREATURE
        ));
        filter.setLockedFilter(true);
    }

    public DanithaSwordOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever you cast an Equipment spell or a spell that targets a creature you control, draw a card. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter, false
        ).setTriggersLimitEachTurn(1));
    }

    private DanithaSwordOfHope(final DanithaSwordOfHope card) {
        super(card);
    }

    @Override
    public DanithaSwordOfHope copy() {
        return new DanithaSwordOfHope(this);
    }
}

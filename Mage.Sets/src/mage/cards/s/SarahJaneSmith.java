package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterHistoricSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SarahJaneSmith extends CardImpl {

    private static final FilterSpell filter = new FilterHistoricSpell();

    public SarahJaneSmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a historic spell, investigate. This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new InvestigateEffect(), filter, false
        ).setTriggersOnceEachTurn(true));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private SarahJaneSmith(final SarahJaneSmith card) {
        super(card);
    }

    @Override
    public SarahJaneSmith copy() {
        return new SarahJaneSmith(this);
    }
}

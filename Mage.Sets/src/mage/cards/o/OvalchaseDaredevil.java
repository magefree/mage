package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class OvalchaseDaredevil extends CardImpl {

    public OvalchaseDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Whenever an artifact enters the battlefield under your control, you may return Ovalchase Daredevil from your graveyard to your hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), StaticFilters.FILTER_PERMANENT_ARTIFACT,
                true, SetTargetPointer.NONE
        ));
    }

    private OvalchaseDaredevil(final OvalchaseDaredevil card) {
        super(card);
    }

    @Override
    public OvalchaseDaredevil copy() {
        return new OvalchaseDaredevil(this);
    }
}

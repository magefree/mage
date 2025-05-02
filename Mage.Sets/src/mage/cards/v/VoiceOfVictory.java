package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantCastDuringYourTurnEffect;
import mage.abilities.keyword.MobilizeAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class VoiceOfVictory extends CardImpl {

    public VoiceOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Mobilize 2
        this.addAbility(new MobilizeAbility(2));

        // Your opponents can't cast spells during your turn.
        this.addAbility(new SimpleStaticAbility(new CantCastDuringYourTurnEffect()));
    }

    private VoiceOfVictory(final VoiceOfVictory card) {
        super(card);
    }

    @Override
    public VoiceOfVictory copy() {
        return new VoiceOfVictory(this);
    }
}

package mage.cards.s;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SpiritualVisit extends CardImpl {

    public SpiritualVisit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");
        this.subtype.add(SubType.ARCANE);

        // Create a 1/1 colorless Spirit creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritToken()));
        
        // Splice onto Arcane {W}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{W}"));
    }

    private SpiritualVisit(final SpiritualVisit card) {
        super(card);
    }

    @Override
    public SpiritualVisit copy() {
        return new SpiritualVisit(this);
    }
}

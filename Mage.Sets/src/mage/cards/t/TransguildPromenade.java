package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TransguildPromenade extends CardImpl {

    public TransguildPromenade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Transguild Promenade enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Transguild Promenade enters the battlefield, sacrifice it unless you pay {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)).setText("sacrifice it unless you pay {1}")
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private TransguildPromenade(final TransguildPromenade card) {
        super(card);
    }

    @Override
    public TransguildPromenade copy() {
        return new TransguildPromenade(this);
    }
}

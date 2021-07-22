package mage.cards.a;

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
 * @author TheElk801
 */
public final class ArchwayCommons extends CardImpl {

    public ArchwayCommons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Archway Commons enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Archway Commons enters the battlefield, sacrifice it unless you pay {1}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new GenericManaCost(1)).setText("sacrifice it unless you pay {1}")
        ));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ArchwayCommons(final ArchwayCommons card) {
        super(card);
    }

    @Override
    public ArchwayCommons copy() {
        return new ArchwayCommons(this);
    }
}

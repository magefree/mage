
package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class VirulentEmissary extends CardImpl {

    public VirulentEmissary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature you control enters, you gain 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new GainLifeEffect(1), StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL));
    }

    private VirulentEmissary(final VirulentEmissary card) {
        super(card);
    }

    @Override
    public VirulentEmissary copy() {
        return new VirulentEmissary(this);
    }
}

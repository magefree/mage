package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.PopulateEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WayfaringTemple extends CardImpl {

    public WayfaringTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Wayfaring Temple's power and toughness are each equal to the number of creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance))
                .addHint(CreaturesYouControlHint.instance));

        // Whenever Wayfaring Temple deals combat damage to a player, populate. (Create a token that's a copy of a creature token you control.)
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PopulateEffect(), false));
    }

    private WayfaringTemple(final WayfaringTemple card) {
        super(card);
    }

    @Override
    public WayfaringTemple copy() {
        return new WayfaringTemple(this);
    }
}

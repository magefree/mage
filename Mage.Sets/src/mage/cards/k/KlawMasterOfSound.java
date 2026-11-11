package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.PlayLandOrCastSpellTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CastManaAdjustment;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

public final class KlawMasterOfSound extends CardImpl {

    public KlawMasterOfSound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL, SubType.ROGUE, SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DeathtouchAbility.getInstance());
        this.addAbility(new PlayLandOrCastSpellTriggeredAbility(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn),
                true, false
        ));
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(
                        false, CastManaAdjustment.AS_THOUGH_ANY_MANA_TYPE
                ).setText("look at the top card of that player's library, then exile it face down. "
                        + "You may play it for as long as it remains exiled, and mana of any type can be spent to "
                        + "cast it"),
                false, true
        ));
    }

    private KlawMasterOfSound(final KlawMasterOfSound card) {
        super(card);
    }

    @Override
    public KlawMasterOfSound copy() {
        return new KlawMasterOfSound(this);
    }
}

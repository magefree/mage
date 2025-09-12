package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author North
 */
public final class TovolarsMagehunter extends CardImpl {

    public TovolarsMagehunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever an opponent casts a spell, Tovolar's Magehunter deals 2 damage to that player.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(2, true, "that player"),
                StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.PLAYER
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Tovolar's Magehunter.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private TovolarsMagehunter(final TovolarsMagehunter card) {
        super(card);
    }

    @Override
    public TovolarsMagehunter copy() {
        return new TovolarsMagehunter(this);
    }
}

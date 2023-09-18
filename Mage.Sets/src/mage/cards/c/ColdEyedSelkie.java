package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ColdEyedSelkie extends CardImpl {

    public ColdEyedSelkie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/U}{G/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Islandwalk
        this.addAbility(new IslandwalkAbility());

        // Whenever Cold-Eyed Selkie deals combat damage to a player, you may draw that many cards.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY),
                true, true));
    }

    private ColdEyedSelkie(final ColdEyedSelkie card) {
        super(card);
    }

    @Override
    public ColdEyedSelkie copy() {
        return new ColdEyedSelkie(this);
    }
}

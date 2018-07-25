package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.target.common.TargetOpponent;

/**
 *
 * @author NinthWorld
 */
public final class HighTemplar extends CardImpl {

    public HighTemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When High Templar enters the battlefield, target opponent sacrifices an attacking or blocking creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SacrificeEffect(new FilterAttackingOrBlockingCreature(), 1, "target opponent"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public HighTemplar(final HighTemplar card) {
        super(card);
    }

    @Override
    public HighTemplar copy() {
        return new HighTemplar(this);
    }
}
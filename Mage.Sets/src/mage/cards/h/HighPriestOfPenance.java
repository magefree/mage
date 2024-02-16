package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class HighPriestOfPenance extends CardImpl {

    public HighPriestOfPenance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        //Whenever High Priest of Penance is dealt damage, you may destroy target nonland permanent.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);
    }

    private HighPriestOfPenance(final HighPriestOfPenance card) {
        super(card);
    }

    @Override
    public HighPriestOfPenance copy() {
        return new HighPriestOfPenance(this);
    }
}

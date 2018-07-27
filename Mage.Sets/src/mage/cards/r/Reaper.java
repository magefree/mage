package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class Reaper extends CardImpl {

    public Reaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Reaper attacks, target player draws a card, then discards a card at random.
        Ability ability = new AttacksTriggeredAbility(new DrawCardTargetEffect(1).setText("target player draws a card"), false);
        ability.addEffect(new DiscardTargetEffect(1, true).setText("then discards a card at random"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public Reaper(final Reaper card) {
        super(card);
    }

    @Override
    public Reaper copy() {
        return new Reaper(this);
    }
}

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class Mutalisk extends CardImpl {

    public Mutalisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Mutalisk can't block.
        this.addAbility(new CantBlockAbility());

        // When Mutalisk enters the battlefield, target player discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public Mutalisk(final Mutalisk card) {
        super(card);
    }

    @Override
    public Mutalisk copy() {
        return new Mutalisk(this);
    }
}

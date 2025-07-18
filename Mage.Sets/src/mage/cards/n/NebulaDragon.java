package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class NebulaDragon extends CardImpl {

    public NebulaDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, it deals 3 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private NebulaDragon(final NebulaDragon card) {
        super(card);
    }

    @Override
    public NebulaDragon copy() {
        return new NebulaDragon(this);
    }
}

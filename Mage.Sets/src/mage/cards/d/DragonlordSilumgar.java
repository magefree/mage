package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class DragonlordSilumgar extends CardImpl {

    public DragonlordSilumgar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Dragonlord Silumgar enters the battlefield, gain control of target creature or planeswalker for as long as you control Dragonlord Silumgar.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled));
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);
    }

    private DragonlordSilumgar(final DragonlordSilumgar card) {
        super(card);
    }

    @Override
    public DragonlordSilumgar copy() {
        return new DragonlordSilumgar(this);
    }
}

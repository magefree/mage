package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AzoriusJusticiar extends CardImpl {

    public AzoriusJusticiar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Azorius Justiciar enters the battlefield, detain up to two target creatures your opponents control. 
        // (Until your next turn, those creatures can't attack or block and their activated abilities can't be activated.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new DetainTargetEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private AzoriusJusticiar(final AzoriusJusticiar card) {
        super(card);
    }

    @Override
    public AzoriusJusticiar copy() {
        return new AzoriusJusticiar(this);
    }
}

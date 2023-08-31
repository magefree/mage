
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class ViridianCorrupter extends CardImpl {

    public ViridianCorrupter (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(InfectAbility.getInstance());

        // When Viridian Corrupter enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private ViridianCorrupter(final ViridianCorrupter card) {
        super(card);
    }

    @Override
    public ViridianCorrupter copy() {
        return new ViridianCorrupter(this);
    }
}

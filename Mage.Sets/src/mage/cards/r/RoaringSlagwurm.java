
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author djbrez
 */
public final class RoaringSlagwurm extends CardImpl {

    public RoaringSlagwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Whenever Roaring Slagwurm attacks, tap all artifacts.
        Ability ability = new AttacksTriggeredAbility(new TapAllEffect(new FilterArtifactPermanent("artifacts")), false);
        this.addAbility(ability);
    }

    private RoaringSlagwurm(final RoaringSlagwurm card) {
        super(card);
    }

    @Override
    public RoaringSlagwurm copy() {
        return new RoaringSlagwurm(this);
    }
}

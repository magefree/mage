
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class EpitaphGolem extends CardImpl {

    public EpitaphGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {2}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new PutOnLibraryTargetEffect(false),
                new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private EpitaphGolem(final EpitaphGolem card) {
        super(card);
    }

    @Override
    public EpitaphGolem copy() {
        return new EpitaphGolem(this);
    }
}

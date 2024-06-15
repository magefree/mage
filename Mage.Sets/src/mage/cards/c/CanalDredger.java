package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author tiera3 - based on Junktroller
 * note - draftmatters ability not implemented
 */
public final class CanalDredger extends CardImpl {

    public CanalDredger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // {tap}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibraryTargetEffect(false), new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    private CanalDredger(final CanalDredger card) {
        super(card);
    }

    @Override
    public CanalDredger copy() {
        return new CanalDredger(this);
    }
}

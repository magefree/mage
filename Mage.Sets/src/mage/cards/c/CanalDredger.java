
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author L_J
 * note - draftmatters ability not implemented
 */
public final class CanalDredger extends CardImpl {

    public CanalDredger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(5);

        // TODO: Draft specific abilities not implemented
        // Draft Canal Dredger face up.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Draft {this} face up - not implemented.")));

        // Each player passes the last card from each booster pack to a player who drafted a card named Canal Dredger.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("Each player passes the last card from each booster pack to a player who drafted a card named Canal Dredger - not implemented.")));

        // {T}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new PutOnLibraryTargetEffect(false), new TapSourceCost());
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


package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPlayer;

/**
 *
 * @author L_J
 */
public final class Scandalmonger extends CardImpl {

    public Scandalmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.MONGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target player discards a card. Any player may activate this ability but only any time they could cast a sorcery.
        ActivateAsSorceryActivatedAbility ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetPlayer());
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private Scandalmonger(final Scandalmonger card) {
        super(card);
    }

    @Override
    public Scandalmonger copy() {
        return new Scandalmonger(this);
    }
}

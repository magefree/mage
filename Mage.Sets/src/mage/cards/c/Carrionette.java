
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessTargetPlayerOrTargetsControllerPaysEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class Carrionette extends CardImpl {

    public Carrionette(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}{B}: Exile Carrionette and target creature unless that creature's controller pays {2}. Activate this ability only if Carrionette is in your graveyard.
        DoUnlessTargetPlayerOrTargetsControllerPaysEffect effect = new DoUnlessTargetPlayerOrTargetsControllerPaysEffect(new ExileTargetEffect(), new ManaCostsImpl<>("{2}"));
        effect.addEffect(new ExileSourceEffect());
        effect.setText("Exile {this} and target creature unless that creature's controller pays {2}. Activate only if {this} is in your graveyard");
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, effect, new ManaCostsImpl<>("{2}{B}{B}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Carrionette(final Carrionette card) {
        super(card);
    }

    @Override
    public Carrionette copy() {
        return new Carrionette(this);
    }
}

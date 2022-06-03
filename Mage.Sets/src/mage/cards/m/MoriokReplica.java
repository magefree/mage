

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class MoriokReplica extends CardImpl {

    public MoriokReplica (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // {1}{B}, Sacrifice Moriok Replica: You draw two cards and you lose 2 life.
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText("You draw two cards");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{1}{B}"));
        effect = new LoseLifeSourceControllerEffect(2);
        effect.setText("and you lose 2 life");
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public MoriokReplica (final MoriokReplica card) {
        super(card);
    }

    @Override
    public MoriokReplica copy() {
        return new MoriokReplica(this);
    }

}

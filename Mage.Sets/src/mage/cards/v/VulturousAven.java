
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class VulturousAven extends CardImpl {

    public VulturousAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Exploit
        this.addAbility(new ExploitAbility());

        // When Vulturous Aven exploits a creature, you draw two cards and you lose 2 life.
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText("you draw two cards");
        Ability ability = new ExploitCreatureTriggeredAbility(effect, false);
        effect = new LoseLifeSourceControllerEffect(2);
        effect.setText("and you lose 2 life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private VulturousAven(final VulturousAven card) {
        super(card);
    }

    @Override
    public VulturousAven copy() {
        return new VulturousAven(this);
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.game.permanent.token.SquirrelToken;

/**
 *
 * @author LevelX2
 */
public final class AcornHarvest extends CardImpl {

    public AcornHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Create two 1/1 green Squirrel creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SquirrelToken(), 2));

        // Flashback-{1}{G} - Pay 3 life.
        FlashbackAbility ability = new FlashbackAbility(this, new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new PayLifeCost(3));
        this.addAbility(ability);
        
    }

    private AcornHarvest(final AcornHarvest card) {
        super(card);
    }

    @Override
    public AcornHarvest copy() {
        return new AcornHarvest(this);
    }
}

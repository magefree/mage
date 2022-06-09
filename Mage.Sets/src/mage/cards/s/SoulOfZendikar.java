
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.BeastToken;

/**
 *
 * @author LevelX2
 */
public final class SoulOfZendikar extends CardImpl {

    public SoulOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // {3}{G}{G}: Create a 3/3 green Beast creature token.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new BeastToken()), new ManaCostsImpl<>("{3}{G}{G}")));
        // {3}{G}{G}, Exile Soul of Zendikar from your graveyard: Create a 3/3 green Beast creature token.
        Ability ability = new SimpleActivatedAbility(Zone.GRAVEYARD, new CreateTokenEffect(new BeastToken()), new ManaCostsImpl<>("{3}{G}{G}"));
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private SoulOfZendikar(final SoulOfZendikar card) {
        super(card);
    }

    @Override
    public SoulOfZendikar copy() {
        return new SoulOfZendikar(this);
    }
}

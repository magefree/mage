
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinRogueToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Loki
 */
public final class WeirdingShaman extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public WeirdingShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new GoblinRogueToken(), 2), new ManaCostsImpl<>("{3}{B}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private WeirdingShaman(final WeirdingShaman card) {
        super(card);
    }

    @Override
    public WeirdingShaman copy() {
        return new WeirdingShaman(this);
    }
}

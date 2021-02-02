
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.RequirementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GoblinDiplomats extends CardImpl {

    public GoblinDiplomats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Each creature attacks this turn if able.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinDiplomatsEffect(), new TapSourceCost()));
        
    }

    private GoblinDiplomats(final GoblinDiplomats card) {
        super(card);
    }

    @Override
    public GoblinDiplomats copy() {
        return new GoblinDiplomats(this);
    }
}

class GoblinDiplomatsEffect extends RequirementEffect {

    public GoblinDiplomatsEffect() {
        super(Duration.EndOfTurn);
        this.staticText = "Each creature attacks this turn if able";
    }

    public GoblinDiplomatsEffect(final GoblinDiplomatsEffect effect) {
        super(effect);
    }

    @Override
    public GoblinDiplomatsEffect copy() {
        return new GoblinDiplomatsEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }
}

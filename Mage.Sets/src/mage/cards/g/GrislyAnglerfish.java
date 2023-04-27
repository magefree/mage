
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author fireshoes
 */
public final class GrislyAnglerfish extends CardImpl {

    public GrislyAnglerfish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // {6}: Creatures your opponents control attack this turn if able.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrislyAnglerfishMustAttackEffect(), new ManaCostsImpl<>("{6}")));
    }

    private GrislyAnglerfish(final GrislyAnglerfish card) {
        super(card);
    }

    @Override
    public GrislyAnglerfish copy() {
        return new GrislyAnglerfish(this);
    }
}

class GrislyAnglerfishMustAttackEffect extends RequirementEffect {

    public GrislyAnglerfishMustAttackEffect() {
        super(Duration.EndOfTurn);
        staticText = "Creatures your opponents control attack this turn if able";
    }

    public GrislyAnglerfishMustAttackEffect(final GrislyAnglerfishMustAttackEffect effect) {
        super(effect);
    }

    @Override
    public GrislyAnglerfishMustAttackEffect copy() {
        return new GrislyAnglerfishMustAttackEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
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

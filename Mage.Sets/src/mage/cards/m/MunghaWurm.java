
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MunghaWurm extends CardImpl {

    public MunghaWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // You can't untap more than one land during your untap step.
         this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MunghaWurmEffect()));
    }

    private MunghaWurm(final MunghaWurm card) {
        super(card);
    }

    @Override
    public MunghaWurm copy() {
        return new MunghaWurm(this);
    }
}

class MunghaWurmEffect extends RestrictionUntapNotMoreThanEffect {

    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent();

    public MunghaWurmEffect() {
        super(Duration.WhileOnBattlefield, 1, filter);
        staticText = "Players can't untap more than one land during their untap steps";
    }

    public MunghaWurmEffect(final MunghaWurmEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        // applied to all players
        return true;
    }

    @Override
    public MunghaWurmEffect copy() {
        return new MunghaWurmEffect(this);
    }

}

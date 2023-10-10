
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public final class DragonHunter extends CardImpl {

    public DragonHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from Dragons
        this.addAbility(new ProtectionAbility(new FilterPermanent(SubType.DRAGON, "Dragons")));

        // Dragon Hunter can block Dragons as though it had reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockDragonsAsThoughtIthadReachEffect(Duration.WhileOnBattlefield)));

    }

    private DragonHunter(final DragonHunter card) {
        super(card);
    }

    @Override
    public DragonHunter copy() {
        return new DragonHunter(this);
    }
}
class CanBlockDragonsAsThoughtIthadReachEffect extends AsThoughEffectImpl {

    public CanBlockDragonsAsThoughtIthadReachEffect(Duration duration) {
        super(AsThoughEffectType.BLOCK_DRAGON, duration, Outcome.Benefit);
        staticText = "{this} can block Dragons as though it had reach";
    }

    private CanBlockDragonsAsThoughtIthadReachEffect(final CanBlockDragonsAsThoughtIthadReachEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CanBlockDragonsAsThoughtIthadReachEffect copy() {
        return new CanBlockDragonsAsThoughtIthadReachEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return sourceId.equals(source.getSourceId());
    }

}

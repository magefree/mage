
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class MasakoTheHumorless extends CardImpl {

    public MasakoTheHumorless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Tapped creatures you control can block as though they were untapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BlockTappedEffect()));
    }

    private MasakoTheHumorless(final MasakoTheHumorless card) {
        super(card);
    }

    @Override
    public MasakoTheHumorless copy() {
        return new MasakoTheHumorless(this);
    }
}

class BlockTappedEffect extends AsThoughEffectImpl {

    public BlockTappedEffect() {
        super(AsThoughEffectType.BLOCK_TAPPED, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText ="Tapped creatures you control can block as though they were untapped";
    }

    public BlockTappedEffect(final BlockTappedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public BlockTappedEffect copy() {
        return new BlockTappedEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null && permanent.isControlledBy(source.getControllerId())) {
            return true;
        }
        return false;
    }
}

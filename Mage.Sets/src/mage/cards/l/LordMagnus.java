
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

/**
 *
 * @author L_J
 */
public final class LordMagnus extends CardImpl {

    public LordMagnus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Creatures with plainswalk can be blocked as though they didn't have plainswalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LordMagnusFirstEffect()));

        // Creatures with forestwalk can be blocked as though they didn't have forestwalk.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LordMagnusSecondEffect()));
    }

    private LordMagnus(final LordMagnus card) {
        super(card);
    }

    @Override
    public LordMagnus copy() {
        return new LordMagnus(this);
    }
}

class LordMagnusFirstEffect extends AsThoughEffectImpl {

    public LordMagnusFirstEffect() {
        super(AsThoughEffectType.BLOCK_PLAINSWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with plainswalk can be blocked as though they didn't have plainswalk";
    }

    public LordMagnusFirstEffect(final LordMagnusFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LordMagnusFirstEffect copy() {
        return new LordMagnusFirstEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}

class LordMagnusSecondEffect extends AsThoughEffectImpl {

    public LordMagnusSecondEffect() {
        super(AsThoughEffectType.BLOCK_FORESTWALK, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Creatures with forestwalk can be blocked as though they didn't have forestwalk";
    }

    public LordMagnusSecondEffect(final LordMagnusSecondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LordMagnusSecondEffect copy() {
        return new LordMagnusSecondEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}

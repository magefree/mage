package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class AbsorbingManAndTitania extends CardImpl {

    public AbsorbingManAndTitania(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Double all damage that creature sources you control would deal.
        this.addAbility(new SimpleStaticAbility(new AbsorbingManAndTitaniaEffect()));
    }

    private AbsorbingManAndTitania(final AbsorbingManAndTitania card) {
        super(card);
    }

    @Override
    public AbsorbingManAndTitania copy() {
        return new AbsorbingManAndTitania(this);
    }
}

class AbsorbingManAndTitaniaEffect extends ReplacementEffectImpl {

    AbsorbingManAndTitaniaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "double all damage that creature sources you control would deal";
    }

    private AbsorbingManAndTitaniaEffect(final AbsorbingManAndTitaniaEffect effect) {
        super(effect);
    }

    @Override
    public AbsorbingManAndTitaniaEffect copy() {
        return new AbsorbingManAndTitaniaEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
            && permanent.isCreature(game)
            && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}

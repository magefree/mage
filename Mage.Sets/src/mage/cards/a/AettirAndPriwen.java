package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AettirAndPriwen extends CardImpl {

    public AettirAndPriwen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has base power and toughness X/X, where X is your life total.
        this.addAbility(new SimpleStaticAbility(new AettirAndPriwenEffect()));

        // Equip {5}
        this.addAbility(new EquipAbility(5));
    }

    private AettirAndPriwen(final AettirAndPriwen card) {
        super(card);
    }

    @Override
    public AettirAndPriwen copy() {
        return new AettirAndPriwen(this);
    }
}

class AettirAndPriwenEffect extends ContinuousEffectImpl {

    AettirAndPriwenEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.Benefit);
        staticText = "equipped creature has base power and toughness X/X, where X is your life total";
    }

    private AettirAndPriwenEffect(final AettirAndPriwenEffect effect) {
        super(effect);
    }

    @Override
    public AettirAndPriwenEffect copy() {
        return new AettirAndPriwenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentIfItStillExists(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(Player::getLife)
                .ifPresent(life -> {
                    permanent.getPower().setModifiedBaseValue(life);
                    permanent.getToughness().setModifiedBaseValue(life);
                });
        return true;
    }
}

package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZilorthaApexOfIkoria extends CardImpl {

    public ZilorthaApexOfIkoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);
        this.color.setGreen(true);
        this.nightCard = true;

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // For each non-Human creature you control, you may have that creature assign its combat damage as though it weren't blocked.
        this.addAbility(new SimpleStaticAbility(new ZilorthaApexOfIkoriaEffect()));
    }

    private ZilorthaApexOfIkoria(final ZilorthaApexOfIkoria card) {
        super(card);
    }

    @Override
    public ZilorthaApexOfIkoria copy() {
        return new ZilorthaApexOfIkoria(this);
    }
}

class ZilorthaApexOfIkoriaEffect extends AsThoughEffectImpl {

    ZilorthaApexOfIkoriaEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "for each non-Human creature you control, you may have that " +
                "creature assign its combat damage as though it weren't blocked";
    }

    private ZilorthaApexOfIkoriaEffect(ZilorthaApexOfIkoriaEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(sourceId);
        return controller != null
                && permanent != null
                && permanent.isControlledBy(controller.getId())
                && !permanent.hasSubtype(SubType.HUMAN, game)
                && controller.chooseUse(Outcome.Damage, "Have " + permanent.getLogName()
                + " assign damage as though it weren't blocked?", source, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ZilorthaApexOfIkoriaEffect copy() {
        return new ZilorthaApexOfIkoriaEffect(this);
    }
}

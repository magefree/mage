package mage.cards.f;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Ninja11Token;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenAttachSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class FootChopper extends CardImpl {

    public FootChopper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 1/1 black Ninja creature token, then attach this Equipment to it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAttachSourceEffect(new Ninja11Token(), " and")));

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT)
        ));

        // Whenever equipped creature deals combat damage to a player, you may sacrifice it. If you do, draw cards equal to its power.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
            new FootChopperEffect(), "equipped", false
        ));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private FootChopper(final FootChopper card) {
        super(card);
    }

    @Override
    public FootChopper copy() {
        return new FootChopper(this);
    }
}

class FootChopperEffect extends OneShotEffect {

    FootChopperEffect() {
        super(Outcome.DrawCard);
        staticText = "you may sacrifice it. If you do, draw cards equal to its power";
    }

    private FootChopperEffect(final FootChopperEffect effect) {
        super(effect);
    }

    @Override
    public FootChopperEffect copy() {
        return new FootChopperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent equipment = source.getSourcePermanentOrLKI(game);
        Permanent attacker = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
        if (player == null || equipment == null || attacker == null
            || !player.chooseUse(outcome, "Sacrifice " + attacker.getName() + '?', source, game)
            || !attacker.sacrifice(source, game)
        ) {
            return false;
        }

        player.drawCards(attacker.getPower().getValue(), source, game);
        return true;
    }
}

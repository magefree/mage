package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CursedWindbreaker extends CardImpl {

    public CursedWindbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Cursed Windbreaker enters, manifest dread, then attach Cursed Windbreaker to that creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CursedWindbreakerEffect()));

        // Equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private CursedWindbreaker(final CursedWindbreaker card) {
        super(card);
    }

    @Override
    public CursedWindbreaker copy() {
        return new CursedWindbreaker(this);
    }
}

class CursedWindbreakerEffect extends OneShotEffect {

    CursedWindbreakerEffect() {
        super(Outcome.Benefit);
        staticText = "manifest dread, then attach {this} to that creature";
    }

    private CursedWindbreakerEffect(final CursedWindbreakerEffect effect) {
        super(effect);
    }

    @Override
    public CursedWindbreakerEffect copy() {
        return new CursedWindbreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent creature = ManifestDreadEffect.doManifestDread(player, source, game);
        Permanent equipment = source.getSourcePermanentIfItStillExists(game);
        return creature != null
                && equipment != null
                && creature.addAttachment(equipment.getId(), source, game);
    }
}

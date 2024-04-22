package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class AndurilFlameOfTheWest extends CardImpl {

    public AndurilFlameOfTheWest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 1)));

        // Whenever equipped creature attacks, create two tapped 1/1 white Spirit creature tokens with flying.
        // If that creature is legendary, instead create two of those tokens that are tapped and attacking.
        this.addAbility(new AttacksAttachedTriggeredAbility(new AndurilFlameOfTheWestEffect(), AttachmentType.EQUIPMENT, false, SetTargetPointer.PERMANENT));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private AndurilFlameOfTheWest(final AndurilFlameOfTheWest card) {
        super(card);
    }

    @Override
    public AndurilFlameOfTheWest copy() {
        return new AndurilFlameOfTheWest(this);
    }
}

class AndurilFlameOfTheWestEffect extends OneShotEffect {

    AndurilFlameOfTheWestEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "create two tapped 1/1 white Spirit creature tokens with flying. " +
                "If that creature is legendary, instead create two of those tokens that are tapped and attacking";
    }

    private AndurilFlameOfTheWestEffect(final AndurilFlameOfTheWestEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipped = this.getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        return new SpiritWhiteToken().putOntoBattlefield(2, game, source, source.getControllerId(), true, equipped.isLegendary(game));
    }

    @Override
    public AndurilFlameOfTheWestEffect copy() {
        return new AndurilFlameOfTheWestEffect(this);
    }
}

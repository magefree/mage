package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SouthWindAvatar extends CardImpl {

    public SouthWindAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another creature you control dies, you gain life equal to its toughness.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new SouthWindAvatarEffect(), false, StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        ));

        // Whenever you gain life, each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private SouthWindAvatar(final SouthWindAvatar card) {
        super(card);
    }

    @Override
    public SouthWindAvatar copy() {
        return new SouthWindAvatar(this);
    }
}

class SouthWindAvatarEffect extends OneShotEffect {

    SouthWindAvatarEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to its toughness";
    }

    private SouthWindAvatarEffect(final SouthWindAvatarEffect effect) {
        super(effect);
    }

    @Override
    public SouthWindAvatarEffect copy() {
        return new SouthWindAvatarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature != null) {
            int toughness = creature.getToughness().getValue();
            if (controller != null) {
                controller.gainLife(toughness, game, source);
                return true;
            }
        }
        return false;
    }
}

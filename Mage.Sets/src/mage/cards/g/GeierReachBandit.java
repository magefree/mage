package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GeierReachBandit extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WEREWOLF, "a Werewolf");

    public GeierReachBandit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.ROGUE, SubType.WEREWOLF}, "{2}{R}",
                "Vildin-Pack Alpha",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );

        // Geier Reach Bandit
        this.getLeftHalfCard().setPT(3, 2);

        // Haste
        this.getLeftHalfCard().addAbility(HasteAbility.getInstance());

        // At the beginning of each upkeep, if no spells were cast last turn, transform Geier Reach Bandit.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Vildin-Pack Alpha
        this.getRightHalfCard().setPT(4, 3);

        // Whenever a Werewolf you control enters, you may transform it.
        this.getRightHalfCard().addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new VildinPackAlphaEffect(), filter,
                true, SetTargetPointer.PERMANENT
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Vildin-Pack Alpha.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private GeierReachBandit(final GeierReachBandit card) {
        super(card);
    }

    @Override
    public GeierReachBandit copy() {
        return new GeierReachBandit(this);
    }
}

class VildinPackAlphaEffect extends OneShotEffect {

    VildinPackAlphaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may transform it";
    }

    private VildinPackAlphaEffect(final VildinPackAlphaEffect effect) {
        super(effect);
    }

    @Override
    public VildinPackAlphaEffect copy() {
        return new VildinPackAlphaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent werewolf = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (werewolf != null) {
            werewolf.transform(source, game);
        }
        return true;
    }
}

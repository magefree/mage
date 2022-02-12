package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class VildinPackAlpha extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.WEREWOLF, "a Werewolf");

    public VildinPackAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        this.nightCard = true;

        // Whenever a Werewolf enters the battlefield under your control, you may transform it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new VildinPackAlphaEffect(), filter,
                true, SetTargetPointer.PERMANENT, null
        ));

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Vildin-Pack Alpha.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private VildinPackAlpha(final VildinPackAlpha card) {
        super(card);
    }

    @Override
    public VildinPackAlpha copy() {
        return new VildinPackAlpha(this);
    }
}

class VildinPackAlphaEffect extends OneShotEffect {

    public VildinPackAlphaEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may transform it";
    }

    public VildinPackAlphaEffect(final VildinPackAlphaEffect effect) {
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

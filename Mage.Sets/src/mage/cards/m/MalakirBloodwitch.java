package mage.cards.m;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class MalakirBloodwitch extends CardImpl {

    public MalakirBloodwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Protection from white
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE));

        // When Malakir Bloodwitch enters the battlefield, each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MalakirBloodwitchEffect(), false)
                .addHint(new ValueHint("Vampires you control", new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.VAMPIRE))))
        );
    }

    private MalakirBloodwitch(final MalakirBloodwitch card) {
        super(card);
    }

    @Override
    public MalakirBloodwitch copy() {
        return new MalakirBloodwitch(this);
    }
}

class MalakirBloodwitchEffect extends OneShotEffect {

    public MalakirBloodwitchEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent loses life equal to the number of Vampires you control. You gain life equal to the life lost this way";
    }

    private MalakirBloodwitchEffect(final MalakirBloodwitchEffect effect) {
        super(effect);
    }

    @Override
    public MalakirBloodwitchEffect copy() {
        return new MalakirBloodwitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent("Vampire");
        filter.add(SubType.VAMPIRE.getPredicate());
        int amount = game.getBattlefield().countAll(filter, source.getControllerId(), game);
        Set<UUID> opponents = game.getOpponents(source.getControllerId());

        int total = 0;
        for (UUID opponentUuid : opponents) {
            Player opponent = game.getPlayer(opponentUuid);
            if (opponent != null) {
                total += opponent.loseLife(amount, game, source, false);
            }
        }
        if (total > 0) {
            player.gainLife(total, game, source);
        }

        return true;
    }
}

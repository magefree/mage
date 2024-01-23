package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgrusKosSpiritOfJustice extends CardImpl {

    public AgrusKosSpiritOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Agrus Kos, Spirit of Justice enters the battlefield or attacks, choose up to one target creature. If it's suspected, exile it. Otherwise, suspect it.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new AgrusKosSpiritOfJusticeEffect());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private AgrusKosSpiritOfJustice(final AgrusKosSpiritOfJustice card) {
        super(card);
    }

    @Override
    public AgrusKosSpiritOfJustice copy() {
        return new AgrusKosSpiritOfJustice(this);
    }
}

class AgrusKosSpiritOfJusticeEffect extends OneShotEffect {

    AgrusKosSpiritOfJusticeEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target creature. If it's suspected, exile it. Otherwise, suspect it";
    }

    private AgrusKosSpiritOfJusticeEffect(final AgrusKosSpiritOfJusticeEffect effect) {
        super(effect);
    }

    @Override
    public AgrusKosSpiritOfJusticeEffect copy() {
        return new AgrusKosSpiritOfJusticeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (!permanent.isSuspected()) {
            permanent.setSuspected(true, game, source);
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(permanent, Zone.EXILED, source, game);
    }
}

package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class MurderousRedcap extends CardImpl {

    public MurderousRedcap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/R}{B/R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ASSASSIN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Murderous Redcap enters the battlefield, it deals damage equal to its power to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MurderousRedcapEffect());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
        // Persist
        this.addAbility(new PersistAbility());
    }

    private MurderousRedcap(final MurderousRedcap card) {
        super(card);
    }

    @Override
    public MurderousRedcap copy() {
        return new MurderousRedcap(this);
    }
}

class MurderousRedcapEffect extends OneShotEffect {

    public MurderousRedcapEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to its power to any target";
    }

    private MurderousRedcapEffect(final MurderousRedcapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent != null && permanent != null) {
            permanent.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game, false, true);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (sourcePermanent != null && player != null) {
            player.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public MurderousRedcapEffect copy() {
        return new MurderousRedcapEffect(this);
    }
}

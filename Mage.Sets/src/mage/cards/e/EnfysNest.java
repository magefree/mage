package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class EnfysNest extends CardImpl {

    public EnfysNest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Enfys Nest enters the battlefield, you may exile target creature an opponent controls. If you do, that player gains life equal to that creature's power.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EnfysNestEffect(), true);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private EnfysNest(final EnfysNest card) {
        super(card);
    }

    @Override
    public EnfysNest copy() {
        return new EnfysNest(this);
    }
}

class EnfysNestEffect extends ExileTargetEffect {

    public EnfysNestEffect() {
        super();
        staticText = "you may exile target creature an opponent controls. If you do, that player gains life equal to that creature's power";
    }

    public EnfysNestEffect(final EnfysNestEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if(permanent != null) {
            // you may exile target creature an opponent controls
            if(!super.apply(game, source)) {
                return false;
            }

            // If you do, that player gains life equal to that creature's power.
            Player player = game.getPlayer(permanent.getControllerId());
            if(player != null) {
                player.gainLife(permanent.getPower().getValue(), game, source);

                return true;
            }
        }
        return false;
    }

    @Override
    public EnfysNestEffect copy() {
        return new EnfysNestEffect(this);
    }
}
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpaceflightAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class TIESilencer extends CardImpl {

    public TIESilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{B}{R}");
        
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        // Whenever TIE Silencer attacks, it deals 1 damage to defending player and 1 damage to up to one target creature that player controls.
        Ability ability = new AttacksTriggeredAbility(new TIESilencerEffect(), false);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private TIESilencer(final TIESilencer card) {
        super(card);
    }

    @Override
    public TIESilencer copy() {
        return new TIESilencer(this);
    }
}

class TIESilencerEffect extends OneShotEffect {

    public TIESilencerEffect() {
        super(Outcome.Damage);
        staticText = "it deals 1 damage to defending player and 1 damage to up to one target creature that player controls";
    }

    public TIESilencerEffect(final TIESilencerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defender = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        if(defender != null) {
            game.damagePlayerOrPermanent(defender, 1, source.getSourceId(), source, game, false, true);

            UUID target = source.getTargets().getFirstTarget();
            Permanent permanent = game.getPermanent(target);
            if(permanent != null) {
                permanent.damage(1, source.getSourceId(), source, game, false, true);
            }

            return true;
        }
        return false;
    }

    public TIESilencerEffect copy() {
        return new TIESilencerEffect(this);
    }
}
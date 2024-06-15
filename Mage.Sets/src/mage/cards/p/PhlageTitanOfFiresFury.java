package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EscapeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PhlageTitanOfFiresFury extends CardImpl {

    public PhlageTitanOfFiresFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Phlage enters the battlefield, sacrifice it unless it escaped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PhlageTitanOfFiresFuryEffect()));

        // Whenever Phlage enters the battlefield or attacks, it deals 3 damage to any target and you gain 3 life.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addEffect(new GainLifeEffect(3).concatBy("and"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // Escape-{R}{R}{W}{W}, Exile five other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{R}{R}{W}{W}", 5));
    }

    private PhlageTitanOfFiresFury(final PhlageTitanOfFiresFury card) {
        super(card);
    }

    @Override
    public PhlageTitanOfFiresFury copy() {
        return new PhlageTitanOfFiresFury(this);
    }
}

class PhlageTitanOfFiresFuryEffect extends OneShotEffect {

    PhlageTitanOfFiresFuryEffect() {
        super(Outcome.Sacrifice);
        staticText = "sacrifice it unless it escaped";
    }

    private PhlageTitanOfFiresFuryEffect(final PhlageTitanOfFiresFuryEffect effect) {
        super(effect);
    }

    @Override
    public PhlageTitanOfFiresFuryEffect copy() {
        return new PhlageTitanOfFiresFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        if (EscapeAbility.wasCastedWithEscape(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter())) {
            return false;
        }
        return permanent.sacrifice(source, game);
    }
}
